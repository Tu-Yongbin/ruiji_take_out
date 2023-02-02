package com.itheima.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ruiji.common.CustomException;
import com.itheima.ruiji.entity.Category;
import com.itheima.ruiji.entity.Dish;
import com.itheima.ruiji.entity.Setmeal;
import com.itheima.ruiji.mapper.CategoryMapper;
import com.itheima.ruiji.service.CategoryService;
import com.itheima.ruiji.service.DishService;
import com.itheima.ruiji.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: CategoryServiceImpl
 * @description:分类的实现
 * @date 2022/12/18 17:37
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    /*
    根据id删除分类，删除之前要判断是否关联菜品/套餐
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishQueryWrapper);

        //查询当前分类是否关联菜品，如果关联则跑一个业务异常
        if (count1 > 0) {
            //已经关联了菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，无法删除!");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = dishService.count(dishQueryWrapper);
        //查询当前分类是否关联套餐，如果关联，则抛出一个业务异常
        if (count2 > 0) {
            //已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，无法删除!");
        }

        //正常删除,调用父亲的方法
        super.removeById(id);
    }
}
