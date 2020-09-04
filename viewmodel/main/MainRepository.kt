package com.yk.silence.customnode.viewmodel.main

import com.yk.silence.customnode.db.helper.RoomHelper
import com.yk.silence.customnode.db.mine.MyselfModel

class MainRepository {
    /**
     * 插入我的信息
     */
    suspend fun insertInfo(list: MyselfModel) = RoomHelper.insertMineInfo(list)
}