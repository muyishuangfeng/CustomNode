package com.yk.silence.customnode.widget.fragment

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.common.REQUEST_ADD_NODE_CODE
import com.yk.silence.customnode.databinding.FragmentHomeBinding
import com.yk.silence.customnode.util.EventBus
import com.yk.silence.customnode.viewmodel.home.HomeViewModel
import com.yk.silence.customnode.widget.activity.AddNodeActivity
import com.yk.silence.customnode.widget.activity.DetailActivity
import com.yk.silence.customnode.widget.adapter.HomeAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.include_reload.view.*

class HomeFragment : BaseVMFragment<HomeViewModel, FragmentHomeBinding>() {

    private lateinit var mAdapter: HomeAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getFragmentID() = R.layout.fragment_home

    override fun viewModelClass() = HomeViewModel::class.java

    override fun initBinding(mBinding: FragmentHomeBinding) {
        mBinding.titleHome.run {
            setTitleClickListener(object : CustomTitleBar.TitleClickListener {
                override fun onLeftClick() {
                }

                override fun onRightClick() {
                    ActivityManager.start(AddNodeActivity::class.java)
                }
            })
        }
        mBinding.srlHome.run {
            setColorSchemeResources(R.color.colorAccent)
            setProgressBackgroundColorSchemeResource(R.color.colorWhite)
            setOnRefreshListener { mViewModel.getData() }
        }
        mAdapter = HomeAdapter().apply {
            bindToRecyclerView(mBinding.rlvHome)
            mOnItemClickListener = {
                val model = mAdapter.data[it]

            }
            mOnItemDeleteListener = {
                val model = mAdapter.data[it]
                mViewModel.deleteData(model)
                mViewModel.getData()
            }
        }
        mBinding.reloadView.btnReload.setOnClickListener {
            mViewModel.getData()
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.getData()
    }


    override fun observe() {
        super.observe()
        mViewModel.run {
            mNodeList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            mRefreshStatus.observe(viewLifecycleOwner, Observer {
                mBinding.srlHome.isRefreshing = it
            })
            mReloadStatus.observe(viewLifecycleOwner, Observer {
                mBinding.reloadView.isVisible = it
            })
            mEmptyStatus.observe(viewLifecycleOwner, Observer {
                mBinding.emptyView.isVisible = it
            })
        }

    }


}