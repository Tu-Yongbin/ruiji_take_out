package com.itheima.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ruiji.entity.User;
import com.itheima.ruiji.mapper.UserMapper;
import com.itheima.ruiji.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: UserServiceImpl
 * @description:
 * @date 2022/12/20 19:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
