package com.yk.silence.customnode.viewmodel.node

import com.yk.silence.customnode.db.node.RoomHelper

class AddNodeRepository {
    /**
     * 查询最大ID
     */
    suspend fun queryMAXID() = RoomHelper.searchMAXID()
}