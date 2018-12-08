package com.doraro.permission_fast.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="UserUpdateForm", description="系统用户更新")
public class UserUpdateForm {
    @NotNull(message = "修改的用户id不能为空")
    private Long userId;
    @ApiModelProperty(value = "0-未激活 1-正常 2-禁用")
    @Range(min = 0,max = 2,message = "status不合法")
    private Integer status;
    private String email;
}
