package org.advisor.message.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestMessage {

    @Email
    private String email; // 이메일 형식

    private Long seq; // 회원 번호

    private String bid; // 게시글 id

    @NotBlank
    private String gid; // 그룹 id

    @NotBlank
    private String subject; // 제목

    @NotBlank
    private String content; // 내용

    /*
    private List<FileInfo> editorImages;
    private List<FileInfo> attachFiles;
     */
}
