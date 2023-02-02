package com.itheima.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ruiji.entity.Employee;
import com.itheima.ruiji.mapper.EmployeeMapper;
import com.itheima.ruiji.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: EmployeeServiceImpl
 * @description:
 * @date 2022/12/15 18:53
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
