package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：sean
 * @date ：Created in 2022/4/18
 * @description ：
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
	implements CategoryService{

	@Autowired
	private DishService dishService;

	@Autowired
	private SetmealService setmealService;


	@Override
	public void remove(Long id) {
		//根据分类id查询菜品数据，如果已经关联，抛出一个业务异常
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(Dish::getCategoryId,id);
		int count = dishService.count(queryWrapper);
		if(count>0){
			throw  new CustomException("分类下有商品，不能删除分类");
		}

		//根据分类id查询套餐数据，如果已经关联，抛出一个业务异常
		LambdaQueryWrapper<Setmeal> queryWrapperSetmeal = new LambdaQueryWrapper();
		queryWrapperSetmeal.eq(Setmeal::getCategoryId,id);
		int count1 = setmealService.count(queryWrapperSetmeal);
		if(count1>0){
			throw new CustomException("当前分类下关联了套餐，不能删除");
		}
		removeById(id);
	}
}
