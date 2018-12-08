package com.doraro.permission_fast.form;

import com.baomidou.mybatisplus.annotation.TableField;
import com.doraro.permission_fast.validator.AddGroup;
import com.doraro.permission_fast.validator.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserAddForm {
    @NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message="密码不能为空", groups = AddGroup.class)
    private String password;


    @NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String mail;

}
