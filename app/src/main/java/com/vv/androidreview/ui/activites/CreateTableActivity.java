/*
 * Copyright (c) 2016. Vv <envyfan@qq.com><http://www.v-sounds.com/>
 *
 * This file is part of AndroidReview (Android面试复习)
 *
 * AndroidReview is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  AndroidReview is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with AndroidReview.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.vv.androidreview.ui.activites;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseActivity;
import com.vv.androidreview.entity.Content;
import com.vv.androidreview.entity.Point;
import com.vv.androidreview.entity.Suggest;
import com.vv.androidreview.entity.Test;
import com.vv.androidreview.entity.Unit;

import org.w3c.dom.Text;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SaveListener;

public class CreateTableActivity extends BaseActivity {
    public static final int PB_STEP = 20;
    private TextView mBtCreateTable;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);
        initToolBar();
        showOrHideToolBarNavigation(true);
//        initData();
        mBtCreateTable = (TextView) findViewById(R.id.bt_create);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mBtCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setProgress(0);
                initData();
            }
        });
    }

    private void initData() {
        final Unit unit = new Unit();
        unit.setName("单元名称");
        unit.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                stepComplete();
                final Point point = new Point();
                point.setColor(2);
                point.setName("知识点");
                point.setUnit(unit);
                point.save(CreateTableActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        stepComplete();
                        Content content = new Content();
                        content.setPoint(point);
                        content.setTitle("博文标题");
                        content.setSource("AndroidReview");
                        content.setCreater("Vv");
                        content.setAuthor("Vv");
                        content.setSmall("博文简介");
                        content.setContent("博文内容");

                        content.save(CreateTableActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                stepComplete();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(CreateTableActivity.this, "创建Content表失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(CreateTableActivity.this, "创建Point表失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(CreateTableActivity.this, "创建Unit表失败", Toast.LENGTH_SHORT).show();
            }
        });

        initTest();

        Suggest suggest = new Suggest();
        suggest.setMail_qq("qq");
        suggest.setMsg("建议");
        suggest.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                stepComplete();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(CreateTableActivity.this, "创建Suggest表失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.creat_table);
    }

    public void initTest() {
        BmobQuery<Test> query = new BmobQuery<>();
        query.count(this, Test.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                stepComplete();
                createTest(i);
            }

            @Override
            public void onFailure(int i, String s) {
                //101代表表格未创建，官网可查 错误码含义http://docs.bmob.cn/errorcode/index.html?menukey=otherdoc&key=errorcode
                if(i==101){
                    stepComplete();
                    createTest(0);
                }else {
                    Toast.makeText(CreateTableActivity.this, "获取Test题目数量失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void stepComplete() {
        mProgressBar.setProgress(mProgressBar.getProgress() + PB_STEP);
    }

    private void createTest(int i) {
        Test test = new Test();
        test.setQuestion("题目");
        test.setTestId(i);//这个作者脑残了设置成了 手动添加并且要从0开始不能重复 重复后会有错（乱序读题根据这个id），设成自增长就不用烦这事了，但是这个要到后台去修改这个字段，读者可以自行尝试
        test.setTestType(1);
        test.setAnswerA("选项");
        test.setAnswerB("选项");
        test.setAnswerC("选项");
        test.setAnswerD("选项");
        test.setAnswerE("选项");
        test.setAnswerF("选项");
        test.setAnswerG("选项");
        test.setAnswer("答案");//如果是要简答题那么前面选项都为空并且设置testType就可以了
        test.save(CreateTableActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                stepComplete();
            }

            @Override
            public void onFailure(int i, String s) {

                Toast.makeText(CreateTableActivity.this, "创建Test表失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
