package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishFlavorMapper;
import com.itheima.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author ：sean
 * @date ：Created in 2022/4/19
 * @description ：
 * @version: 1.0
 */
@Service
public class DishFlavorServiceImpl
	extends ServiceImpl<DishFlavorMapper, DishFlavor>
	implements DishFlavorService {
}