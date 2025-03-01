package com.example.project9.batch;

import com.example.project9.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    // Step 구성
    @Bean
    public Step permanentlyDeletePostsStep() {
        return new StepBuilder("permanentlyDeletePostsStep", jobRepository)
                .<Post, Post>chunk(100, transactionManager)
                .reader(softDeletedPostReader())
                .writer(postPermanentDeleteWriter())
                .build();
    }

    // Job 구성
    @Bean
    public Job deleteOldSoftDeletedPostsJob() {
        return new JobBuilder("deleteOldSoftDeletedPostsJob", jobRepository)
                .start(permanentlyDeletePostsStep())
                .build();
    }

    // Reader: Soft Delete된 지 30일 이상 된 Post 조회
    @Bean
    public JpaPagingItemReader<Post> softDeletedPostReader() {

        return new JpaPagingItemReaderBuilder<Post>()
                .name("softDeletedPostReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .queryString("SELECT p FROM Post p WHERE p.deletedAt IS NOT NULL")
                .build();
    }

    // Writer: 실제 DB 삭제 (Soft Delete 우회)
    @Bean
    public ItemWriter<Post> postPermanentDeleteWriter() {
        return items -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                for (Post post : items) {
                    // Native Query로 직접 삭제 (Soft Delete 로직 무시)
                    entityManager.createNativeQuery("DELETE FROM post WHERE id = :id")
                            .setParameter("id", post.getId())
                            .executeUpdate();
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) transaction.rollback();
                throw new RuntimeException("Permanent delete failed", e);
            } finally {
                entityManager.close();
            }
        };
    }
}