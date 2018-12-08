package com.doraro.permission_fast.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.doraro.permission_fast.model.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class PageView {
    //总记录数
    private int totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currPage;
    //列表数据
    private List<?> list;
    public PageView(IPage<?> page) {
        this.list = page.getRecords();
        this.totalCount = (int)page.getTotal();
        this.pageSize = (int)page.getSize();
        this.currPage = (int)page.getCurrent();
        this.totalPage = (int)page.getPages();
    }
}
