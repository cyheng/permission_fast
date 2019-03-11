package com.doraro.permission_fast.controller;

import com.doraro.permission_fast.form.SysLoginForm;
import com.doraro.permission_fast.model.SysUser;
import com.doraro.permission_fast.service.ISysUserService;
import com.doraro.permission_fast.service.SysCaptchaService;
import com.doraro.permission_fast.service.SysTokenService;
import com.doraro.permission_fast.util.EncptyUtils;
import com.doraro.permission_fast.exception.Result;
import com.doraro.permission_fast.validator.ValidatorUtils;
import com.wf.captcha.Captcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.doraro.permission_fast.exception.CodeMsg.CAPTCHA_ERROR;
import static com.doraro.permission_fast.exception.CodeMsg.INVALID_USR_OR_PWD;

@RestController
public class SysLoginController {
    @Autowired
    private SysCaptchaService captchaService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysTokenService tokenService;
    @GetMapping(value = "captcha.jpg",produces = MediaType.IMAGE_GIF_VALUE)
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        CaptchaUtil.setHeader(response);
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        captchaService.saveCaptcha(uuid,gifCaptcha.text().toLowerCase());
        gifCaptcha.out(response.getOutputStream());
    }

    @PostMapping("/sys/login")
    public Result login(@RequestBody SysLoginForm sysLoginForm){
        ValidatorUtils.validate(sysLoginForm);
        boolean capCheck = captchaService.checkCaptcha(sysLoginForm.getUuid(), sysLoginForm.getCaptcha());
        if(!capCheck){
            return Result.error(CAPTCHA_ERROR);
        }
        SysUser user =  userService.selectActiveUserByUsername(sysLoginForm.getUsername());
        if (user == null || !user.getPassword().equals(EncptyUtils.formPwdToDb(sysLoginForm.getPassword(),user.getSalt()))){
            return Result.error(INVALID_USR_OR_PWD);
        }
        String token = tokenService.genToken(user);
        return Result.ok().put("token",token);
    }


}
