package com.doraro.permission_fast.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
@TableName("sys_module")

public class SysModule implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "module_id", type = IdType.ID_WORKER)
    private Long moduleId;


    @TableField("parent_id")
    private Long parentId;


    @TableField("module_sname")
    private String moduleSname;


    @TableField("perms")
    private String perms;


}
