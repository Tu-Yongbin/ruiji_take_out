package com.itheima.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ruiji.common.CustomException;
import com.itheima.ruiji.dto.SetmealDto;
import com.itheima.ruiji.entity.Setmeal;
import com.itheima.ruiji.entity.SetmealDish;
import com.itheima.ruiji.mapper.SetMealMapper;
import com.itheima.ruiji.service.SetMealDishService;
import com.itheima.ruiji.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: SetMealServiceImpl
 * @description:
 * @date 2022/12/18 19:19
 */
@Service
@Slf4j
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetMealDishService setMealDishService;

    //新增套餐，保存套餐和菜品的关联关系
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息，操作setmeal
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关系信息，setmealdish
        setMealDishService.saveBatch(setmealDishes);
    }

    //删除套餐，同时删除套餐和菜品的关联关系
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，确定套餐是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);

        //如果不能删除，抛出一个业务异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //删除关系表中的数据---setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setMealDishService.remove(lambdaQueryWrapper);
    }
}
