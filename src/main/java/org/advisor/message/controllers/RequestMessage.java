package org.advisor.message.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



import java.util.List;

@Data
public class RequestMessage {


    @Email
    private String email;

    @NotBlank
    private String gid;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    /*
    private List<FileInfo> editorImages;
    private List<FileInfo> attachFiles;
     */
}
