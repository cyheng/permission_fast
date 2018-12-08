package com.doraro.permission_fast.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")

public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "role_id", type = IdType.ID_WORKER)
    private Long roleId;


    @TableField("role_name")
    private String roleName;


    @TableField("comment")
    private String comment;


    @TableField("create_user_id")
    private Long createUserId;

    @TableField("create_time")
    private LocalDateTime createTime;


}
