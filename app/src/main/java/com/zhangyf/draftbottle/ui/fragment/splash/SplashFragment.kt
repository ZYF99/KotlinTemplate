package com.zhangyf.draftbottle.ui.fragment.splash


import android.Manifest
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.databinding.FragmentSplashBinding
import com.zhangyf.draftbottle.ui.activity.LoginActivity

import com.zhangyf.draftbottle.ui.base.BaseFragment
import io.reactivex.Observable
import java.io.IOException
import java.util.concurrent.TimeUnit

const val TOTAL_TIME = 3

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    SplashViewModel::class.java,
    layoutRes = R.layout.fragment_splash
) {

    override fun initView() {

    }

    override fun initData() {
        activity?.let {
            RxPermissions(it).request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).flatMap { success ->
                if (success) Observable.interval(1, TimeUnit.SECONDS)
                    .take((TOTAL_TIME + 1).toLong())
                else throw NoPermissionError()
            }.doOnEach { num ->
                viewModel.time.postValue("${TOTAL_TIME - (num.value?.toInt() ?: TOTAL_TIME)}秒后跳转")
            }.doOnComplete {
                context?.let { it1 -> startActivity(LoginActivity.createIntent(it1)) }
            }.doOnError {
                if (it is NoPermissionError) activity?.finish()
            }.bindLife()
        }
    }

    data class NoPermissionError(val description: String = "") : IOException()
}
