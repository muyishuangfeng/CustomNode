package com.yk.silence.customnode.widget.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.google.android.material.animation.AnimationUtils
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.databinding.ActivityMainBinding
import com.yk.silence.customnode.databinding.FragmentGroundBinding
import com.yk.silence.customnode.ext.showToast
import com.yk.silence.customnode.impl.ScrollToTop
import com.yk.silence.customnode.widget.fragment.ChatFragment
import com.yk.silence.customnode.widget.fragment.GroundFragment
import com.yk.silence.customnode.widget.fragment.HomeFragment
import com.yk.silence.customnode.widget.fragment.MineFragment
import java.io.InterruptedIOException

class MainActivity : BaseActivity<ActivityMainBinding>() {


    private lateinit var mFragments: Map<Int, Fragment>
    private var bottomNavigationViewAnimator: ViewPropertyAnimator? = null
    private var currentBottomNavigationState = true
    private var previousTimeMillis = 0L

    override fun getLayoutID() = R.layout.activity_main

    override fun initSaveState(mBinding: ActivityMainBinding, savedInstanceState: Bundle?) {
        super.initSaveState(mBinding, savedInstanceState)
        mFragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.chat to createFragment(ChatFragment::class.java),
            R.id.ground to createFragment(GroundFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java)
        )
        mBinding.bnvMain.run {
            setOnNavigationItemSelectedListener {
                showFragment(it.itemId)
                true
            }
            setOnNavigationItemReselectedListener { menuItem ->
                val fragment = mFragments.entries.find { it.key == menuItem.itemId }?.value
                if (fragment is ScrollToTop)
                    fragment.scrollToTop()
            }
        }
        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            mBinding.bnvMain.selectedItemId = initialItemId
            showFragment(initialItemId)
        }
    }


    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            showToast(R.string.text_press_exit)
            previousTimeMillis = currentTime
        }
    }


    /**
     * 创建Fragment
     */
    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                ChatFragment::class.java -> ChatFragment.newInstance()
                GroundFragment::class.java -> GroundFragment.newInstance()
                MineFragment::class.java -> MineFragment.newInstance()
                else -> throw IllegalArgumentException("argument${clazz.simpleName} is illega")
            }
        }
        return fragment
    }

    /**
     * 显示Fragment
     */
    private fun showFragment(menuID: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in mFragments.values
        }
        val targetFragment = mFragments.entries.find { it.key == menuID }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let { if (it.isAdded) show(it) else add(R.id.fly_container, it) }
        }.commit()
    }


    /**
     * 动画效果
     */
    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavigationState == show) {
            return
        }
        if (bottomNavigationViewAnimator != null) {
            bottomNavigationViewAnimator?.cancel()
            mBinding.bnvMain.clearAnimation()
        }
        currentBottomNavigationState = show
        val targetY = if (show) 0F else mBinding.bnvMain.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimator = mBinding.bnvMain.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimator = null
                }
            })
    }

    override fun onDestroy() {
        bottomNavigationViewAnimator?.cancel()
        mBinding.bnvMain.clearAnimation()
        bottomNavigationViewAnimator = null
        super.onDestroy()
    }
}