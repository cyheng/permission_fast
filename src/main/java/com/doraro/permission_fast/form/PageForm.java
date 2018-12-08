package com.doraro.permission_fast.form;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class PageForm{
    @ApiParam(value = "页码(默认1)",defaultValue = "1")
    private int page = 1;
    @ApiParam(value = "每页条数(默认5)",defaultValue = "5")
    private int limit = 5;
    @ApiParam(value = "升序排序字段,逗号分割，如：name,email")
    private String asidx;
    @ApiParam(value = "降序排序字段,逗号分割，如：name,email")
    private String dsidx;

    public static <T> Page<T> toQueryPage(PageForm param){
        final Page<T> query = new Page<>(param.page,param.limit);
        if(StringUtils.isNotBlank(param.asidx)){
            final String[] asc = param.asidx.split(",");
            query.setAsc(asc);
        }
        if(StringUtils.isNotBlank(param.dsidx)){
            final String[] desc = param.dsidx.split(",");
            query.setDesc(desc);
        }
        return query;
    }
}
