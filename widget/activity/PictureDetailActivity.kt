package com.yk.silence.customnode.widget.activity

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Fade
import android.transition.TransitionSet
import androidx.core.transition.doOnEnd
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.databinding.ActivityPicDetailBinding
import com.yk.silence.customnode.util.glide.load

class PictureDetailActivity : BaseActivity<ActivityPicDetailBinding>() {



    override fun getLayoutID() = R.layout.activity_pic_detail

    override fun initBinding(binding: ActivityPicDetailBinding) {
        super.initBinding(binding)
        val mUrl = intent?.getStringExtra(PARAM_ARTICLE) ?: return
        supportPostponeEnterTransition()
        binding.photoView.transitionName = mUrl
        binding.photoView.load(mUrl, true) {
            supportStartPostponedEnterTransition()
        }
        window.sharedElementEnterTransition = TransitionSet()
            .addTransition(ChangeImageTransform())
            .addTransition(ChangeBounds())
            .apply {
                doOnEnd { binding.photoView.load(mUrl) }
            }
        window.enterTransition = Fade().apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
            excludeTarget(R.id.action_bar_container, true)
        }
        binding.setOnClick {
            onBackPressed()
        }
    }

}