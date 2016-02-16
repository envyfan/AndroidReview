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
