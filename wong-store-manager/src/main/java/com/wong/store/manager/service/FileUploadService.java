package com.wong.store.manager.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Jay Wong
 * @date 2023/12/23 22:28
 */
public interface FileUploadService {
    // 上传文件到 minio
    String upload(MultipartFile file);
}
