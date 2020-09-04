package com.yk.silence.customnode.widget.activity

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.databinding.ActivityAboutBinding
import com.yk.silence.customnode.viewmodel.about.AboutViewModel

class AboutActivity : BaseVMActivity<AboutViewModel, ActivityAboutBinding>() {

    override fun getLayoutID() = R.layout.activity_about

    override fun viewModelClass() = AboutViewModel::class.java

    override fun initBinding(binding: ActivityAboutBinding) {
        super.initBinding(binding)
    }
}