package com.yk.silence.customnode.widget.activity

import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityAddFriendBinding
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.model.UserModel
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.util.ToastUtil
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.viewmodel.friend.FriendViewModel
import com.yk.silence.toolbar.CustomTitleBar

class AddFriendActivity : BaseVMActivity<FriendViewModel, ActivityAddFriendBinding>() {

    private lateinit var mUser: UserModel


    override fun getLayoutID() = R.layout.activity_add_friend

    override fun viewModelClass() = FriendViewModel::class.java

    override fun initBinding(binding: ActivityAddFriendBinding) {
        super.initBinding(binding)
        mUser = intent?.getParcelableExtra(PARAM_ARTICLE) ?: return
        mBinding.friend = mUser
        if (mUser.user_avatar != null)
            GlideUtils.loadPathWithOutCache(
                this,
                mUser.user_avatar,
                mBinding.imgAddPhoto
            )

        mBinding.titleAddFriend.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {
                ActivityManager.finish(AddFriendActivity::class.java)
            }

            override fun onRightClick() {

            }
        })

    }

    override fun initData() {
        super.initData()
        mBinding.btnAddSubmit.setOnClickListener {
            mViewModel.addFriend(mUser.id)
        }
    }


    override fun observer() {
        super.observer()
        mViewModel.run {
            mFriendState.observe(this@AddFriendActivity, Observer {
                EventUtil.send(EventModel(MSG_CODE_ADD_FRIEND))
                ActivityManager.finish(AddFriendActivity::class.java)
            })
            mFriendModel.observe(this@AddFriendActivity, Observer {
                if (it != null) {
                    val model = FriendModel()
                    model.user_name = it.friend_name
                    model.user_id = it.id.toString()
                    model.user_avatar = it.friend_avatar
                    model.friend_time = it.friend_time
                    mViewModel.addFriend(model)
                }

            })


        }
    }


}