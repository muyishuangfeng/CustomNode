package com.yk.silence.customnode.viewmodel.main

import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.db.helper.MyselfInfoHelper

class MainViewModel : BaseViewModel() {

    private val mMainRepository by lazy { MainRepository() }

    fun insertData() {
        launch(
            block = {
                val mList = MyselfInfoHelper.insertMyInfo()
                mList.forEach {
                    mMainRepository.insertInfo(it)
                }
            }
        )
    }
}