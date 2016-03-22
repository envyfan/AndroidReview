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

package com.vv.androidreview.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.entity.Test;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class AnswerItem extends LinearLayout {

    private TextView mChoice, mChoiceContent;
    private Test mTest;

    public AnswerItem(Context context) {
        this(context, null);
    }

    public AnswerItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.test_answer_item, null);
        mChoice = (TextView) view.findViewById(R.id.tv_choice);
        mChoiceContent = (TextView) view.findViewById(R.id.tv_choice_content);
        addView(view,lp);
    }

    public void setChoiceText(String str) {
        mChoice.setText(str);
    }

    public void setChoiceContent(String content) {
        mChoiceContent.setText(content);
    }

    public void setTest(Test test) {
        this.mTest = test;
    }

    public void click() {
//        mChoice.setTextColor(getResources().getColor(R.color.white));
        if (mChoice.getText().toString().equals(mTest.getAnswer())) {
            mChoice.setBackgroundResource(R.mipmap.icon_true);
            mChoice.setText("");
        } else{
            mChoice.setBackgroundResource(R.mipmap.icon_false);
            mChoice.setText("");
        }
    }
}
