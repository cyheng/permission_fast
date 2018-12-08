package com.doraro.permission_fast.form;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleAddForm {
    @NotBlank(message = "角色名不能为空")
    public String roleName;
    public String comment;
    public List<Long> moduleIdList;
}
