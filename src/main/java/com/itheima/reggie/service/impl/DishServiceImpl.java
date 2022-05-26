package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：sean
 * @date ：Created in 2022/4/19
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
	@Autowired
	private DishFlavorService dishFlavorService;


	@Override
	@Transactional
	public void saveWithFlavor(DishDto dishDto) {
		//①. 保存菜品基本信息 ;
		this.save(dishDto);
		//②. 获取保存的菜品ID ;
		Long dishId = dishDto.getId();
		//③. 获取菜品口味列表，遍历列表，为菜品口味对象属性dishId赋值;
		List<DishFlavor> flavors = dishDto.getFlavors();
		for(DishFlavor dishFlavor:flavors){
			dishFlavor.setDishId(dishId);
		}
		log.info("flavors:{}",flavors);
		//④. 批量保存菜品口味列表;
		dishFlavorService.saveBatch(flavors);
	}

	@Override
	public DishDto getByIdWithFlavor(Long id) {
		Dish dish = this.getById(id);
		DishDto dishDto = new DishDto();
		BeanUtils.copyProperties(dish,dishDto);
		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(DishFlavor::getDishId,dish.getId());
		List<DishFlavor> list = dishFlavorService.list(queryWrapper);
		dishDto.setFlavors(list);
		return dishDto;
	}

	@Override
	@Transactional
	public void updateWithFlavor(DishDto dishDto) {
		//1.先更新Dish表
		this.updateById(dishDto);
		//2.删除原来菜品的口味
		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
		dishFlavorService.remove(queryWrapper);
		//3.添加新的口味
		List<DishFlavor> flavors = dishDto.getFlavors();
		for (DishFlavor dishFlavor:flavors){
			dishFlavor.setDishId(dishDto.getId());
		}
		dishFlavorService.saveBatch(flavors);


	}
}
