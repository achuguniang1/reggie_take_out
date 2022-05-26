package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：sean
 * @date ：Created in 2022/4/19
 * @description ：
 * @version: 1.0
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}