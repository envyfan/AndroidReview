package com.vv.androidreview.entity;

import cn.bmob.v3.BmobObject;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class Suggest extends BmobObject{
    private String msg;

    public String getMail_qq() {
        return mail_qq;
    }

    public void setMail_qq(String mail_qq) {
        this.mail_qq = mail_qq;
    }

    private String mail_qq;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
