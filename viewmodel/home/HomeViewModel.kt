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


}