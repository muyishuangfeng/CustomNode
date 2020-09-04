package com.yk.silence.customnode.viewmodel.about

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.db.mine.MyselfModel

class AboutViewModel : BaseViewModel() {

    private val mAboutRepository by lazy { AboutRepository() }

    //我的数据信息
    val mMineList = MutableLiveData<MutableList<MyselfModel>>()

    //刷新状态
    val mRefreshState = MutableLiveData<Boolean>()

    fun getData() {
        mRefreshState.value = true
        launch(
            block = {
                mMineList.value = mAboutRepository.queryData()
                mRefreshState.value = false
            }
        )
    }
}