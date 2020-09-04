package com.yk.silence.customnode.viewmodel.home

import com.yk.silence.customnode.db.node.HomeNode
import com.yk.silence.customnode.db.helper.RoomHelper
import com.yk.silence.customnode.net.retrofit.RetrofitClient

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

    /**
     * 添加笔记
     */
    suspend fun addNode(nodeJson: String, imgList: String) =
        RetrofitClient.apiService.addNode(nodeJson, imgList).apiData()

    /**
     * 删除笔记
     */
    suspend fun deleteNode(id: Int) =
        RetrofitClient.apiService.deleteNode(id).apiData()


}

