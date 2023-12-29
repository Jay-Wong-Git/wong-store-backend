package com.wong.store.manager.test;

import com.wong.store.manager.properties.MinioProperties;
import com.wong.store.manager.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jay Wong
 * @date 2023/12/28 15:55
 */
@SpringBootTest
public class UploadImageTest {
    @Resource
    FileUploadService fileUploadService;

    @Test
    public void uploadImages() {
        File directory = new File("/Users/andywong/Documents/Dev/DevCode/AndyProjects/wong-store-parent/images");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
            if (files != null) {
                for (File image : files) {
                    fileUploadService.uploadImage(image);
                }
            } else {
                System.out.println("No image files found in the specified directory.");
            }
        } else {
            System.out.println("The specified path is not a valid directory or does not exist.");
        }
    }
}
