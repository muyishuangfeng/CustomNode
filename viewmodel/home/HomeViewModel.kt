package com.yk.silence.customnode.viewmodel.home

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.db.node.HomeNode

class HomeViewModel : BaseViewModel() {

    val mHomeRepository: HomeRepository by lazy { HomeRepository() }

    //记录集合
    val mNodeList = MutableLiveData<MutableList<HomeNode>>()

    //重新加载
    val mReloadStatus = MutableLiveData<Boolean>()

    //刷新
    val mRefreshStatus = MutableLiveData<Boolean>()

    //空状态
    val mEmptyStatus = MutableLiveData<Boolean>()

    //添加状态
    val mAddStatus = MutableLiveData<Boolean>()

    //ID
    val mNumber = MutableLiveData<Int>()

    /**
     * 获取数据
     */
    fun getData() {
        mEmptyStatus.value = false
        mReloadStatus.value = false
        mRefreshStatus.value = true
        launch(
            block = {
                val mHomeNode = mHomeRepository.queryAllNode()
                mNodeList.value = mHomeNode.toMutableList()
                mRefreshStatus.value = false
                mReloadStatus.value = false
                mEmptyStatus.value = mHomeNode.isEmpty()
            },
            error = {
                mEmptyStatus.value = true
                mReloadStatus.value = false
                mRefreshStatus.value = false
            }
        )
    }

    /**
     * 删除
     */
    fun deleteData(homeNode: HomeNode) {
        launch(
            block = {
                mHomeRepository.deleteNode(homeNode)
            }
        )
    }

    /**
     * 查询最大ID
     */
    fun queryMAXID() {
        launch(
            block = {
                mNumber.value = mHomeRepository.queryMAXID()
            }
        )
    }

    /**
     * 添加记录
     */
    fun addNode(homeNode: HomeNode) {
        mAddStatus.value = false
        launch(
            block = {
                mHomeRepository.addNode(homeNode)
                mAddStatus.value = true
            }
        )
    }
}