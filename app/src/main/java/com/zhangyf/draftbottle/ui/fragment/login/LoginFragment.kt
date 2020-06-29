package com.zhangyf.draftbottle.ui.fragment.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.databinding.FragmentLoginBinding
import com.zhangyf.draftbottle.ui.activity.MainActivity
import com.zhangyf.draftbottle.ui.base.BaseFragment
import com.zhangyf.draftbottle.ui.fragment.VideoPlayerListener
import io.reactivex.schedulers.Schedulers
import tv.danmaku.ijk.media.player.IjkMediaPlayer


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    LoginViewModel::class.java,
    layoutRes = R.layout.fragment_login
) {
    private val loginVm by lazy { ViewModelProvider(requireActivity()).get<LoginViewModel>() }

    override fun initView() {

        //设置视频地址
        viewModel.videoUrl.observeNonNull {
            binding.videoPlayer.setVideoPath(it)
        }

        binding.loginButton.setOnClickListener {
            viewModel.checkAndLogin()
            startActivity(
                Intent(context, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

    }

    override fun initData() {
        viewModel.init()
        setuUpVideoPlayer()
    }

    //设置背景视频
    private fun setuUpVideoPlayer() {
        Schedulers.io().scheduleDirect {
            //加载native库
            try {
                IjkMediaPlayer.loadLibrariesOnce(null)
                IjkMediaPlayer.native_profileBegin("libijkplayer.so")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            binding.videoPlayer.setListener(VideoPlayerListener())
            viewModel.videoUrl.postValue("android.resource://" + context?.packageName + "/" + R.raw.bg_login_1)
        }
    }
}