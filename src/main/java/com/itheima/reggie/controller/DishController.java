package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：sean
 * @date ：Created in 2022/4/19
 * @description ：
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private DishFlavorService dishFlavorService;

	@PostMapping
	public R<String> save(@RequestBody DishDto dishDto){
		log.info(dishDto.toString());

		dishService.saveWithFlavor(dishDto);

		return R.success("新增菜品成功");
	}

	@GetMapping("/page")
	public R<Page> page(int page, int pageSize, String name){
		//1). 构造分页条件对象
		Page<Dish> pageInfo = new Page<>(page,pageSize);
		// 返回的类型是Page<DishDto>类型的，但是从MP输出的是Page<Dish>
		Page<DishDto> dishDtoPage = new Page<>();
		//2). 构建查询及排序条件
		LambdaQueryWrapper<Dish> pageQueryMapper = new LambdaQueryWrapper();
		pageQueryMapper.like(name != null,Dish::getName,name);
		pageQueryMapper.orderByDesc(Dish::getUpdateTime);
		//3). 执行分页条件查询
		Page<Dish> dishPage = dishService.page(pageInfo, pageQueryMapper);
		// 把数据封装到DishDto中,第3个参数，忽略records
		BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
		//4). 遍历分页查询列表数据, 把Dish对象转为DishDto对象，同时赋值分类名称
		List<Dish> recordsDish = pageInfo.getRecords();
		List<DishDto>   recordsDishDto = recordsDish.stream().map((item)->{
			DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item,dishDto);
			Category category = categoryService.getById(item.getId());
			if(category != null){
				String categoryName = category.getName();
				dishDto.setCategoryName(categoryName);
			}
			return dishDto;
		}).collect(Collectors.toList());
		//5). 封装数据并返回
		dishDtoPage.setRecords(recordsDishDto);
		return R.success(dishDtoPage);
	}

	@GetMapping("/{id}")
	public R<DishDto> get(@PathVariable Long id){
		DishDto dishDto = dishService.getByIdWithFlavor(id);
		if(dishDto != null){
			return R.success(dishDto);
		}else{
			return R.error("无对象");
		}
	}

	@PutMapping
	public R<String> update(@RequestBody DishDto dishDto){
		log.info("[更新:{}]",dishDto);
		dishService.updateWithFlavor(dishDto);
		return R.success("更新成功");
	}
}
