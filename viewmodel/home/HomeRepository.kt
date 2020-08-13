package com.yk.silence.customnode.viewmodel.home

import com.yk.silence.customnode.db.HomeNode
import com.yk.silence.customnode.db.RoomHelper

class HomeRepository {
    /**
     * 查询所有的记录
     */
    suspend fun queryAllNode() = RoomHelper.getAllNodes()

    /**
     * 删除记录
     */
    suspend fun deleteNode(homeNode: HomeNode) = RoomHelper.deleteNode(homeNode)

    /**
     * 添加
     */
    suspend fun addNode(homeNode: HomeNode) = RoomHelper.addNode(homeNode)
}