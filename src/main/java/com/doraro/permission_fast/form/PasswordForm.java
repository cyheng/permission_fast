package com.doraro.permission_fast.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordForm {
    /**
     * 原密码
     */
    @NotBlank
    private String password;
    /**
     * 新密码
     */
    @NotBlank
    private String newPassword;
}
