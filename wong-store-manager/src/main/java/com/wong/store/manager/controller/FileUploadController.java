package com.wong.store.manager.controller;

import com.wong.store.manager.service.FileUploadService;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jay Wong
 * @date 2023/12/23 22:27
 */
@Tag(name = "文件传输管理接口")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;

    /**
     * 上传文件接口
     *
     * @param file 文件对象
     * @return 访问文件的 url
     */
    @PostMapping("/fileUpload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.upload(file);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
