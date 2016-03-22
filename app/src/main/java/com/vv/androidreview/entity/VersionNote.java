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

package com.vv.androidreview.entity;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：记录更新日志
 */
public class VersionNote {

    private String versionName;
    private String updateTime;
    private String updateNote;

    public VersionNote(String versionName, String updateTime, String updateNote) {
        this.versionName = versionName;
        this.updateTime = updateTime;
        this.updateNote = updateNote;
    }

    public String getUpdateNote() {
        return updateNote;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getUpdateTime() {
        return updateTime;
    }


}
