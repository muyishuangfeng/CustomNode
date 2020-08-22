package com.yk.silence.customnode.widget.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.MSG_CODE_ADD_FRIEND
import com.yk.silence.customnode.common.MSG_CODE_ADD_NODE
import com.yk.silence.customnode.databinding.FragmentHomeBinding
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.viewmodel.home.HomeViewModel
import com.yk.silence.customnode.widget.activity.AddNodeActivity
import com.yk.silence.customnode.widget.adapter.HomeAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.include_reload.view.*
import org.greenrobot.eventbus.Subscribe

class HomeFragment : BaseVMFragment<HomeViewModel, FragmentHomeBinding>() {

    private lateinit var mAdapter: HomeAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getFragmentID() = R.layout.fragment_home

    override fun viewModelClass() = HomeViewModel::class.java

    override fun initBinding(mBinding: FragmentHomeBinding) {
        EventUtil.register(this)
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
            mOnItemClickListener = {
                val model = mAdapter.data[it]

            }
            mOnItemDeleteListener = {
                val model = mAdapter.data[it]
                mViewModel.deleteData(model)
                mViewModel.getData()
            }
        }
        mBinding.rlvHome.adapter = mAdapter
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

    @Subscribe
    fun onEvent(event: Any) {
        val mData = event as EventModel
        if (mData.code == MSG_CODE_ADD_NODE) {
            mViewModel.getData()
        }
    }

    override fun onDestroy() {
        EventUtil.unRegister(this)
        super.onDestroy()
    }


}