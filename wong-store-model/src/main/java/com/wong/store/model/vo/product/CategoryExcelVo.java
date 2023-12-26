package com.wong.store.model.vo.product;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExcelVo {

	@ExcelProperty(value = "id" ,index = 0)
	private Long id;

	@ExcelProperty(value = "name" ,index = 1)
	private String name;

	@ExcelProperty(value = "picture url" ,index = 2)
	private String imageUrl ;

	@ExcelProperty(value = "parent id" ,index = 3)
	private Long parentId;

	@ExcelProperty(value = "status" ,index = 4)
	private Integer status;

	@ExcelProperty(value = "sort" ,index = 5)
	private Integer orderNum;

}