package com.wong.store.model.entity.product;

import com.wong.store.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "分类实体类")
public class Category extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -2752560957218008596L;
    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "分类图片url")
    private String imageUrl;

    @Schema(description = "父节点id")
    private Long parentId;

    @Schema(description = "分类状态: 是否显示[0-不显示，1显示]")
    private Integer status;

    @Schema(description = "排序字段")
    private Integer orderNum;

    @Schema(description = "是否存在子节点")
    private Boolean hasChildren;

    @Schema(description = "子节点List集合")
    private List<Category> children;

}