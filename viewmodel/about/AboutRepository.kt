package com.yk.silence.customnode.viewmodel.about

import com.yk.silence.customnode.db.helper.MyselfInfoHelper
import com.yk.silence.customnode.db.helper.RoomHelper

class AboutRepository {

    /**
     * 查询所有数据
     */
    suspend fun queryAllData() = RoomHelper.queryMineInfo()

    suspend fun queryData()=MyselfInfoHelper.insertMyInfo()
}