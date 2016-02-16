package com.vv.androidreview.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Author：Vv on 2016/1/17.
 * Mail：envyfan@qq.com
 * Description：单元
 */
public class Unit extends BmobObject {

    //单元名称
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
