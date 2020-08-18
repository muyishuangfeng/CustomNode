package com.yk.silence.customnode.viewmodel.circle

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.CircleModel

class CircleViewModel : BaseViewModel() {

    val mCircleRepository by lazy { CircleRepository() }
    val mCircleList = MutableLiveData<MutableList<CircleModel>>()
    val mRefreshState = MutableLiveData<Boolean>()
    val mEmptyState = MutableLiveData<Boolean>()

    /**
     * 获取数据
     */
    fun refreshData() {
        mRefreshState.value = true
        mEmptyState.value = false
        launch(
            block = {
                val currentList = mCircleRepository.getData()
                mCircleList.value = currentList
                mRefreshState.value = false
                mEmptyState.value = mCircleList.value?.isEmpty()
            },
            error = {
                mRefreshState.value = false
                mEmptyState.value = true
            }
        )
    }

}