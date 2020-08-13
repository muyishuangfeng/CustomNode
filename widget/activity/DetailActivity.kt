package com.yk.silence.customnode.widget.activity

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    override fun getLayoutID() = R.layout.activity_detail

    override fun initBinding(binding: ActivityDetailBinding) {
        super.initBinding(binding)
        val mUrl = intent?.getStringExtra(PARAM_ARTICLE) ?: return
        binding.photoUrl = mUrl
    }

}