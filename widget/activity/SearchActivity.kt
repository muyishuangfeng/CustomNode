package com.yk.silence.customnode.widget.activity

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.databinding.ActivitySearchBinding
import com.yk.silence.customnode.model.UserModel
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.viewmodel.search.SearchViewModel
import com.yk.silence.toolbar.CustomTitleBar

class SearchActivity : BaseVMActivity<SearchViewModel, ActivitySearchBinding>() {

    private lateinit var mUser: UserModel

    override fun getLayoutID() = R.layout.activity_search

    override fun viewModelClass() = SearchViewModel::class.java

    override fun initBinding(binding: ActivitySearchBinding) {
        super.initBinding(binding)
        binding.titleSearch.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {
                ActivityManager.finish(SearchActivity::class.java)
            }

            override fun onRightClick() {
            }
        })

    }

    override fun initData() {
        super.initData()
        mBinding.btnSearch.setOnClickListener {
            mViewModel.searchUser(mBinding.edtSearch.text.toString().toInt())
        }
        mBinding.ctlSearch.setOnClickListener {
            ActivityManager.start(
                AddFriendActivity::class.java, mapOf(
                    PARAM_ARTICLE to mUser
                )
            )
            ActivityManager.finish(SearchActivity::class.java)
        }

    }


    override fun observer() {
        super.observer()
        mViewModel.run {
            mFriendModel.observe(this@SearchActivity, Observer {
                if (it != null) {
                    mBinding.user = it
                    mBinding.ctlSearch.visibility = View.VISIBLE
                    mUser = it
                    GlideUtils.loadPathWithOutCache(
                        this@SearchActivity,
                        it.user_avatar,
                        mBinding.imgSearchAvatar
                    )
                } else {
                    mBinding.ctlSearch.visibility = View.GONE
                }
            })
        }
    }
}