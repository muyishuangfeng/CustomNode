package com.yk.silence.customnode.widget.activity

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.ActivityCircleBinding
import com.yk.silence.customnode.viewmodel.circle.CircleViewModel
import com.yk.silence.customnode.widget.adapter.CircleAdapter
import com.yk.silence.toolbar.CustomTitleBar

class CircleActivity : BaseVMActivity<CircleViewModel, ActivityCircleBinding>(),
    CustomTitleBar.TitleClickListener {

    private lateinit var mAdapter: CircleAdapter

    override fun getLayoutID() = R.layout.activity_circle

    override fun viewModelClass() = CircleViewModel::class.java

    override fun initBinding(binding: ActivityCircleBinding) {
        super.initBinding(binding)
        binding.titleCircle.setTitleClickListener(this)
        binding.srlCircle.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        mAdapter = CircleAdapter().apply {
            setDiffCallback(CircleAdapter.CircleDiffCallBack())
        }
        binding.rlvCircle.adapter = mAdapter
    }

    override fun onLeftClick() {
        ActivityManager.finish(CircleActivity::class.java)
    }

    override fun onRightClick() {
    }

    override fun initData() {
        super.initData()
        mViewModel.refreshData()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mRefreshState.observe(this@CircleActivity, Observer {
                mBinding.srlCircle.isRefreshing = it
            })

            mEmptyState.observe(this@CircleActivity, Observer {
                mBinding.emptyView.isVisible = it
            })

            mCircleList.observe(this@CircleActivity, Observer {
                mAdapter.setDiffNewData(it)
            })
        }
    }
}