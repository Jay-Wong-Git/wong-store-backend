package com.wong.store.model.entity.h5;

import com.wong.store.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@Schema(description = "购物车实体类")
public class CartInfo extends BaseEntity {
   
   @Serial
   private static final long serialVersionUID = 1L;

   @Schema(description = "用户id")
   private Long userId;

   @Schema(description = "skuId")
   private Long skuId;

   @Schema(description = "放入购物车时价格")
   private BigDecimal cartPrice;

   @Schema(description = "数量")
   private Integer skuNum;

   @Schema(description = "图片文件")
   private String imgUrl;

   @Schema(description = "sku名称 (冗余)")
   private String skuName;

   @Schema(description = "isChecked")
   private Integer isChecked;
    
}