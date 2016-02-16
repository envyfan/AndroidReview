package com.vv.androidreview.entity;

import cn.bmob.v3.BmobObject;

/**
 * Author：Vv on 2016/1/17.
 * Mail：envyfan@qq.com
 * Description：单元--知识点--内容
 */
public class Content extends BmobObject {

    //标题
    private String title;

    //内容正文
    private String content;

    //作者
    private String author;

    //内容来源
    private String source;

    //创建者
    private String creater;

    //所属知识点
    private Point point;

    //简介
    private String small;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
