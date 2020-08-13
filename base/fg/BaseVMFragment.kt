package com.yk.silence.customnode.base.fg

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.yk.silence.customnode.base.vm.BaseViewModel

abstract class BaseVMFragment<VM:BaseViewModel,V:ViewDataBinding>:BaseFragment<V>() {

    protected lateinit var mViewModel: VM
    //懒加载
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        observe()
        // 因为Fragment恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState == null) {
            initData()
        }
    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }


    abstract fun viewModelClass(): Class<VM>

    /**
     * 初始化viewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * 如果有需要初始化
     */
    open fun initData() {}

    /**
     * 如果有需要初始化
     */
    open fun initView() {}

    /**
     * 如果有需要初始化
     */
    open fun lazyLoadData() {
    }

    open fun observe() {

    }



}