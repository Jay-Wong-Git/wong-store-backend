package com.wong.store.product.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.model.dto.h5.ProductSkuDto;
import com.wong.store.model.entity.product.Product;
import com.wong.store.model.entity.product.ProductDetails;
import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.model.vo.h5.ProductItemVo;
import com.wong.store.product.mapper.ProductDetailsMapper;
import com.wong.store.product.mapper.ProductMapper;
import com.wong.store.product.mapper.ProductSkuMapper;
import com.wong.store.product.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay Wong
 * @date 2023/12/28 11:20
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductDetailsMapper productDetailsMapper;

    /**
     * 获取热销Sku
     *
     * @return 热销Sku列表
     */
    @Cacheable(
            value = "product",
            key = "'hot'",
            unless = "#result.size()==0"
    )
    @Override
    public List<ProductSku> queryHotProductSku() {
        return productSkuMapper.queryHotProductSku();
    }

    /**
     * 根据条件分页查询sku
     *
     * @param page          当前页
     * @param limit         每页显示条数
     * @param productSkuDto 参数对象
     * @return Sku列表
     */
    @Cacheable(
            value = "product",
            key = "'#keyword:'+#productSkuDto.keyword" +
                    "+'#brandId:'+#productSkuDto.brandId" +
                    "+'#category3Id:'+#productSkuDto.category3Id" +
                    "+'#order:'+#productSkuDto.order",
            unless = "#result.getSize()==0"
    )
    @Override
    public PageInfo<ProductSku> querySkuByCriteriaByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.queryByCriteria(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    /**
     * 根据skuId查询商品详细数据
     *
     * @param skuId skuId
     * @return 商品详细数据Vo对象
     */
    @Cacheable(
            value = "product",
            key = "'#skuid:'+#skuId",
            unless = "#result==null"
    )
    @Override
    public ProductItemVo item(Long skuId) {
        // 获取Sku信息
        ProductSku productSku = productSkuMapper.queryById(skuId);
        // 获取商品基本信息
        Product product = productMapper.queryById(productSku.getProductId());
        // 获取同一个商品下面的Sku列表
        List<ProductSku> productSkuList = productSkuMapper.queryByProductId(productSku.getProductId());
        //建立sku规格与skuId对应关系
        Map<String, Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item -> skuSpecValueMap.put(item.getSkuSpec(), item.getId()));
        // 获取商品详情信息
        ProductDetails productDetails = productDetailsMapper.queryByProductId(productSku.getProductId());

        // 整理成ProductItemVo对象返回
        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSpecValueList(JSONArray.parseArray(product.getSpecValue()));
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        return productItemVo;
    }

    /**
     * 根据skuId获取Sku
     *
     * @param skuId skuId
     * @return ProductSku对象
     */
    @Override
    public ProductSku getSkuBySkuId(Long skuId) {
        return productSkuMapper.queryById(skuId);
    }
}
