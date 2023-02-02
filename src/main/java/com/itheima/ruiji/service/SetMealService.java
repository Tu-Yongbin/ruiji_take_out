package com.itheima.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ruiji.dto.SetmealDto;
import com.itheima.ruiji.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {
    //新增套餐，保存套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);

    //删除套餐，同时删除套餐和菜品的关联关系
    public void removeWithDish(List<Long> ids);
}
