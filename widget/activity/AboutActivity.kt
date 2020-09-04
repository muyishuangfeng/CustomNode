package com.yk.silence.customnode.widget.activity

import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.databinding.ActivityAboutBinding
import com.yk.silence.customnode.db.mine.MyselfModel
import com.yk.silence.customnode.viewmodel.about.AboutViewModel
import com.yk.silence.customnode.widget.adapter.AboutAdapter
import com.yk.silence.toolbar.CustomTitleBar

class AboutActivity : BaseVMActivity<AboutViewModel, ActivityAboutBinding>() {

    private lateinit var mAdapter: AboutAdapter
    private lateinit var mList: MutableList<MyselfModel>

    override fun getLayoutID() = R.layout.activity_about

    override fun viewModelClass() = AboutViewModel::class.java

    override fun initBinding(binding: ActivityAboutBinding) {
        super.initBinding(binding)
        binding.titleAbout.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {
                ActivityManager.finish(DetailActivity::class.java)
            }

            override fun onRightClick() {

            }
        })
        mBinding.srlAbout.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.getData() }
        }
        mAdapter = AboutAdapter().apply {
            mOnItemClickListener = {
                val model = mList[it]
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(
                        PARAM_ARTICLE to model
                    )
                )
            }
        }
        binding.rlvAbout.adapter = mAdapter

    }

    override fun initData() {
        super.initData()
        mViewModel.getData()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mMineList.observe(this@AboutActivity, Observer {
                mAdapter.setList(it)
                mList = it
            })
            mRefreshState.observe(this@AboutActivity, Observer {
                mBinding.srlAbout.isRefreshing = it
            })
        }
    }
}