package com.doraro.permission_fast.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "user_id", type = IdType.ID_WORKER)
    private Long userId;


    @TableField("username")
    private String username;


    @TableField("password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @TableField("salt")
    @JsonProperty( access = JsonProperty.Access.WRITE_ONLY)
    private String salt;


    @TableField("mail")
    private String mail;


    @TableField("status")
    private Integer status;


    @TableField("create_user_id")
    private Long createUserId;


    @TableField("create_time")
    private LocalDateTime createTime;


}
