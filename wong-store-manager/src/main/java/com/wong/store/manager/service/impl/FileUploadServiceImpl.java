package com.wong.store.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.manager.properties.MinioProperties;
import com.wong.store.manager.service.FileUploadService;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author Jay Wong
 * @date 2023/12/23 22:29
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    private MinioProperties minioProperties;

    /**
     * 上传文件到 minio
     *
     * @param file 文件对象
     */
    @Override
    public String upload(MultipartFile file) {

        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();

            // 判断桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {       // 如果不存在，那么此时就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {  // 如果存在打印信息
                System.out.printf("Bucket %s already exists", minioProperties.getBucketName());
                System.out.println();
            }

            // 设置存储对象名称
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //20230801/443e1e772bef482c95be28704bec58a901.jpg
            String fileName = dateDir + "/" + uuid + file.getOriginalFilename();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .object(fileName)
                    .build();
            minioClient.putObject(putObjectArgs);

            //return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;
            return "http://wongblog.icu:90" + "/" + minioProperties.getBucketName() + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
