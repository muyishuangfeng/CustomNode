package com.yk.silence.customnode.viewmodel.node

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.db.HomeModel
import com.yk.silence.customnode.db.HomeNode
import com.yk.silence.customnode.viewmodel.home.HomeRepository

class AddNodeViewModel : BaseViewModel() {

    private val mNodeRepository by lazy { HomeRepository() }
    private val mAddNodeRepository by lazy { AddNodeRepository() }
    val mNumber = MutableLiveData<Int>()

    /**
     * 添加记录
     */
    fun addNode(homeNode: HomeNode) {
        launch(
            block = {
                mNodeRepository.addNode(homeNode)
            }
        )
    }

    /**
     * 查询最大ID
     */
    fun queryMAXID() {
        launch(
            block = {
                mNumber.value = mAddNodeRepository.queryMAXID()
            }
        )
    }

}