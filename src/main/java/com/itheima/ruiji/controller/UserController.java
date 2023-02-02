package com.itheima.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.ruiji.common.R;
import com.itheima.ruiji.entity.User;
import com.itheima.ruiji.service.UserService;
import com.itheima.ruiji.utils.SMSUtils;
import com.itheima.ruiji.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: UserController
 * @description:
 * @date 2022/12/20 19:28
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /*
    发送验证码
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //随机生产验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            //调用阿里云短信服务api完成发送短信
            //SMSUtils.sendMessage("阿斌外卖", "", phone, code);

            //保存验证码到session
            session.setAttribute(phone, code);
            return R.success("手机验证码发送成功");
        }

        return R.error("手机验证码发送失败");
    }

    /*
    移动端登陆
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从session获取保存的验证码
        Object codeInSession = session.getAttribute(phone);

        //与页面提交的code比对
        if (codeInSession != null && codeInSession.equals(code)) {
            //如果比对成功，登录成功
            //判断手机号用户是否新用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //新用户则自动注册
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
