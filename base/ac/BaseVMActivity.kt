package com.yk.silence.customnode.base.ac

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.yk.silence.customnode.base.vm.BaseViewModel

abstract class BaseVMActivity<VM:BaseViewModel,V:ViewDataBinding>: BaseActivity<V>() {

    protected open lateinit var mViewModel:VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        observer()
        // 因为Activity恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState == null) {
            initData()
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
     * 如有需要可以初始化
     */
    open fun initView() {

    }

    /**
     * 如有需要可以初始化
     */
    open fun initData() {

    }

    /**
     * 如有需要可以初始化
     */
    open fun observer() {

    }



}