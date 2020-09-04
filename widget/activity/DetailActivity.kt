package com.yk.silence.customnode.widget.activity

import android.os.Build
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.PARAM_ARTICLE
import com.yk.silence.customnode.databinding.ActivityDetailBinding
import com.yk.silence.customnode.db.mine.MyselfModel
import com.yk.silence.customnode.ext.SettingsStore
import com.yk.silence.customnode.ext.whiteHostList
import com.yk.silence.customnode.util.JSUtil

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private lateinit var mAboutMine: MyselfModel
    private var agentWeb: AgentWeb? = null

    override fun getLayoutID() = R.layout.activity_detail

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initBinding(binding: ActivityDetailBinding) {
        super.initBinding(binding)
        mAboutMine = intent?.getParcelableExtra(PARAM_ARTICLE) ?: return
        binding.txtDetailTitle.text = mAboutMine.mine_type
        binding.imgDetailBack.setOnClickListener {
            ActivityManager.finish(DetailActivity::class.java)
        }
        initData()
    }

    /**
     * 初始化数据
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initData() {
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.flyWebContainer, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.colorAccent), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.include_reload, R.id.btnReload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    mBinding.txtDetailTitle.text = title
                    super.onReceivedTitle(view, title)
                }

                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    return super.onConsoleMessage(consoleMessage)
                }
            })
            .setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return !whiteHostList().contains(request?.url?.host)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    view?.loadUrl(JSUtil.customJs(url!!))
                }
            })
            .createAgentWeb()
            .ready()
            .get()
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
                textZoom = SettingsStore.getWebTextZoom()
            }
        }
        agentWeb?.urlLoader?.loadUrl(mAboutMine.link)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event) == true) {
            return true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}