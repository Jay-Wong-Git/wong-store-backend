package com.wong.store.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.manager.mapper.ProductDetailMapper;
import com.wong.store.manager.mapper.ProductMapper;
import com.wong.store.manager.mapper.ProductSkuMapper;
import com.wong.store.manager.service.ProductService;
import com.wong.store.model.dto.product.ProductDto;
import com.wong.store.model.entity.product.Product;
import com.wong.store.model.entity.product.ProductDetails;
import com.wong.store.model.entity.product.ProductSku;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/26 19:53
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductDetailMapper productDetailMapper;

    /**
     * 根据条件分页获取商品
     *
     * @param current    当前页
     * @param limit      每页显示条数
     * @param productDto 参数对象
     * @return 商品列表
     */
    @Override
    public PageInfo<Product> queryByCriteriaByPage(Integer current, Integer limit, ProductDto productDto) {
        PageHelper.startPage(current, limit);
        List<Product> productList = productMapper.queryByCriteria(productDto);
        return new PageInfo<>(productList);
    }

    /**
     * 添加商品
     *
     * @param product 参数对象
     */
    @Transactional
    @Override
    public void save(Product product) {
        // 保存商品数据
        product.setStatus(0);              // 设置上架状态为0
        product.setAuditStatus(0);         // 设置审核状态为0
        productMapper.save(product);

        // 保存商品sku数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0, size = productSkuList.size(); i < size; i++) {
            // 获取ProductSku对象
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

            productSku.setProductId(product.getId());               // 设置商品id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);
            productSkuMapper.save(productSku);                    // 保存数据
        }

        // 保存商品详情数据
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailMapper.save(productDetails);
    }

    /**
     * 根据Id获取商品
     *
     * @param id 商品Id
     * @return 商品对象
     */
    @Override
    public Product queryById(Long id) {
        // 获取商品数据
        Product product = productMapper.queryById(id);
        // 获取商品SKU数据
        List<ProductSku> productSkuList = productSkuMapper.queryByProductId(id);
        product.setProductSkuList(productSkuList);
        // 获取商品详情数据
        ProductDetails productDetails = productDetailMapper.queryByProductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;
    }

    /**
     * 修改商品
     *
     * @param product 参数对象
     */
    @Transactional
    @Override
    public void update(Product product) {
        // 修改商品基本数据
        productMapper.update(product);
        // 修改商品SKU数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku productSku : productSkuList) {
            productSkuMapper.update(productSku);
        }
        // 修改商品详情数据
        ProductDetails productDetails = productDetailMapper.queryByProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailMapper.update(productDetails);
    }

    /**
     * 根据Id删除商品
     *
     * @param id 商品Id
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        // 删除商品基本信息
        productMapper.deleteById(id);
        // 删除商品SKU信息
        productSkuMapper.deleteByProductId(id);
        // 删除商品详情信息
        productDetailMapper.deleteByProductId(id);
    }

    /**
     * 更新商品审核状态
     *
     * @param id          商品Id
     * @param auditStatus 商品审核状态
     */
    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if (auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.update(product);
    }

    /**
     * 商品上架或下架
     *
     * @param id     商品Id
     * @param status 商品上架或下架 1上架；-1下架
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if (status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.update(product);
    }
}
