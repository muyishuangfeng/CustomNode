package com.yk.silence.customnode.viewmodel.mine

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.UserModel

class MineViewModel : BaseViewModel() {

    private val mMineRepository by lazy { MineRepository() }

    val mUserModel = MutableLiveData<UserModel>()

    val mUpdateState = MutableLiveData<Boolean>()

    /**
     * 获取数据
     */
    fun getData() {
        launch(
            block = {
                mUserModel.value = mUserRepository.getUserInfo()
            }
        )
    }

    /**
     * 更新用户信息
     */
    fun updateUser(
        username: String,
        password: String,
        user_avatar: String
    ) {
        mUpdateState.value = false
        launch(
            block = {
                val mUser = mMineRepository.updateUser(username, password, user_avatar)
                mUserRepository.updateUserInfo(mUser)
                mUpdateState.value = true
            },
            error = {
                mUpdateState.value = false
            }
        )
    }
}