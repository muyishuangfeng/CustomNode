package com.yk.silence.customnode.viewmodel.home

import com.yk.silence.customnode.db.node.HomeNode
import com.yk.silence.customnode.db.node.RoomHelper

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

    /**
     * 查询最大ID
     */
    suspend fun queryMAXID() = RoomHelper.searchMAXID()
}