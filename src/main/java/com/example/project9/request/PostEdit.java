package com.example.project9.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostEdit {

    @NotBlank
    @Size(max = 10, message = "제목은 10자 이하입니다.")
    private String title;

    @NotBlank
    @Size(max = 100, message = "내용은 100자 이하입니다.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
