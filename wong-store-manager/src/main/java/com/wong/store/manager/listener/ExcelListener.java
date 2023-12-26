package com.wong.store.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.wong.store.manager.mapper.CategoryMapper;
import com.wong.store.model.entity.product.Category;
import com.wong.store.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 10:20
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {
    // 设置多少条存一次数据库
    private static final int BATCH_COUNT = 100;
    // 缓存数据
    private List<CategoryExcelVo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final CategoryMapper categoryMapper;

    // ExcelListener对象不能交给Spring，需要通过构造函数来注入Mapper对象
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    // 每解析一行数据就会调用一次该方法
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        cachedDataList.add((CategoryExcelVo) t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清空list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    // excel解析完毕以后需要执行的代码
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    // 存储数据到数据库
    private void saveData() {
        categoryMapper.saveBatch(cachedDataList);
    }
}
