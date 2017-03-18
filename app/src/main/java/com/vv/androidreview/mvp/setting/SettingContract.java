package com.vv.androidreview.mvp.setting;

import com.vv.androidreview.mvp.base.BasePresenter;
import com.vv.androidreview.mvp.base.BaseView;
import com.vv.androidreview.mvp.exam.ExamContract;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public interface SettingContract {

    interface View extends BaseView<ExamContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
