package com.wong.store.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.manager.listener.ExcelListener;
import com.wong.store.manager.mapper.CategoryMapper;
import com.wong.store.manager.service.CategoryService;
import com.wong.store.model.entity.product.Category;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.product.CategoryExcelVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/25 22:08
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 根据父分类Id获取子分类
     *
     * @param parentId 父分类Id
     * @return 子分类列表
     */
    @Override
    public List<Category> queryByParentId(Long parentId) {
        List<Category> categoryList = categoryMapper.queryByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            // 判断每个分类是否包含子分类
            categoryList.forEach(category -> {
                int countChildren = categoryMapper.countByParentId(category.getId());
                category.setHasChildren(countChildren > 0);
            });
        }
        return categoryList;
    }

    /**
     * 导出数据为Excel
     *
     * @param response 响应对象
     */
    @Override
    public void exportData(HttpServletResponse response) {
        // 设置响应结果类型
        response.setContentType("application/vnd.ms-excel");
        // 设置响应结果编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置导出文件名及编码格式
        String fileName = URLEncoder.encode("CategoryData", StandardCharsets.UTF_8);
        // 设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 查询数据
        List<Category> categoryList = categoryMapper.queryAll();
        // 把Category对象转换成CategoryExcelVo对象
        ArrayList<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());
        for (Category category : categoryList) {
            CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
            BeanUtils.copyProperties(category, categoryExcelVo);
            categoryExcelVoList.add(categoryExcelVo);
        }
        try {
            // 写出数据到浏览器
            EasyExcel
                    .write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("CategoryData") // 工作表名
                    .doWrite(categoryExcelVoList);  // 数据源集合
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     * 从外部Excel导入分类数据接口
     *
     * @param multipartFile 文件对象
     */
    @Override
    public void importData(MultipartFile multipartFile) {
        // 创建监听器对象，并传递Mapper对象
        ExcelListener<Category> listener = new ExcelListener<>(categoryMapper);
        try {
            // 读取数据
            EasyExcel
                    .read(multipartFile.getInputStream(), CategoryExcelVo.class, listener)
                    .sheet("CategoryData") // 工作表名
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
