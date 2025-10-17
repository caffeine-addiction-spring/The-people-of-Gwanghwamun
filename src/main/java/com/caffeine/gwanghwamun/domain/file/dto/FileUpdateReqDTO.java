package com.caffeine.gwanghwamun.domain.file.dto;

import lombok.Data;

@Data
public class FileUpdateReqDTO {
    private String gid;
    private String location;
    private String fileName;
    private String contentType;
    private String extension;
    private String fileUrl;
}
