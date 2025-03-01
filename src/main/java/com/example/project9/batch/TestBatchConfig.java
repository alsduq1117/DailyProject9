package com.example.project9.batch;

import com.example.project9.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class TestBatchConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//
//    // 테스트용 Job 정의
//    @Bean
//    public Job testInsertJob() {
//        return new JobBuilder("testInsertJob", jobRepository)
//                .start(testInsertStep())
//                .listener(jobExecutionListener())
//                .build();
//    }
//
//    // 테스트용 Step 정의
//    @Bean
//    public Step testInsertStep() {
//        return new StepBuilder("testInsertStep", jobRepository)
//                .<Post, Post>chunk(5, transactionManager) // 5개 단위 처리
//                .reader(testPostReader())
//                .processor(post -> {
//                    log.info("Processing: {}", post.getTitle());
//                    return post;
//                })
//                .writer(list -> {
//                    log.info("Writing {} items", list.size());
//                    list.forEach(post -> log.info("Saved: {}", post));
//                })
//                .build();
//    }
//
//    // 테스트 데이터 생성 Reader
//    @Bean
//    public ItemReader<Post> testPostReader() {
//        List<Post> dummyData = IntStream.range(0, 20)
//                .mapToObj(i -> Post.builder()
//                        .title("Test Title " + i)
//                        .content("Test Content " + i)
//                        .build())
//                .collect(Collectors.toList());
//
//        return new IteratorItemReader<>(dummyData);
//    }
//
//    // 실행 리스너
//    @Bean
//    public JobExecutionListener jobExecutionListener() {
//        return new JobExecutionListener() {
//            @Override
//            public void beforeJob(JobExecution jobExecution) {
//                log.info("🚀 테스트 배치 시작");
//            }
//
//            @Override
//            public void afterJob(JobExecution jobExecution) {
//                log.info("✅ 테스트 배치 완료 - 상태: {}", jobExecution.getStatus());
//            }
//        };
//    }
//}
