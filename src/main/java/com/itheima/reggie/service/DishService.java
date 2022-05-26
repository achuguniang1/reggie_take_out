package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @author ：sean
 * @date ：Created in 2022/4/19
 * @description ：
 * @version: 1.0
 */
public interface DishService extends IService<Dish> {
	void saveWithFlavor(DishDto dishDto);
	DishDto getByIdWithFlavor(Long id);

	void updateWithFlavor(DishDto dishDto);
}