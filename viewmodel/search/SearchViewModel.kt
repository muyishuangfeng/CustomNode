package com.yk.silence.customnode.viewmodel.search

import androidx.lifecycle.MutableLiveData
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.model.UserModel

class SearchViewModel : BaseViewModel() {

    private val mSearchRepository by lazy { SearchRepository() }


    //好友信息
    val mFriendModel = MutableLiveData<UserModel>()

    /**
     * 查询好友
     */
    fun searchUser(id: Int) {
        launch(
            block = {
                mFriendModel.value = mSearchRepository.searchFriend(id)
            }
        )
    }
}