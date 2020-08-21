package com.yk.silence.customnode.widget.activity

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.databinding.ActivityAddFriendBinding
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel

class AddFriendActivity : BaseVMActivity<FriendViewModel, ActivityAddFriendBinding>() {

    override fun getLayoutID() = R.layout.activity_add_friend

    override fun viewModelClass() = FriendViewModel::class.java
}