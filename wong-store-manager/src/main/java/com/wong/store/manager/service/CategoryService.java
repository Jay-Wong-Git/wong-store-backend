package com.wong.store.manager.service;

import com.wong.store.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/25 22:08
 */
public interface CategoryService {
    // 根据父分类Id获取子分类
    List<Category> queryByParentId(Long parentId);

    // 导出数据为Excel
    void exportData(HttpServletResponse response);

    // 从外部Excel导入分类数据
    void importData(MultipartFile multipartFile);
}
