package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @author ：sean
 * @date ：Created in 2022/4/18
 * @description ：
 * @version: 1.0
 */
public interface CategoryService extends IService<Category> {
	//根据ID删除分类
	public void remove(Long id);
}
