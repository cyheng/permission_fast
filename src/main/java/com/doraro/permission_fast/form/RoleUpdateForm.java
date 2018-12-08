package com.doraro.permission_fast.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleUpdateForm {
    @NotNull(message = "角色id不能为空")
    private Long roleId;
    @NotBlank(message = "角色名不能为空")
    public String roleName;
    public String comment;
    public List<Long> moduleIdList;
}
