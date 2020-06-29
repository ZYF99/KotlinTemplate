package com.zhangyf.draftbottle.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commitNow
import com.zhangyf.draftbottle.ui.fragment.login.LoginFragment
import com.zhangyf.draftbottle.ui.base.BaseActivity
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private var quiteTime: Long = System.currentTimeMillis()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        supportFragmentManager.commitNow(allowStateLoss = true) {
            add(R.id.login_fragment, LoginFragment())
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - quiteTime > 3000) {
            Toast.makeText(
                this, R.string.exit_info, Toast.LENGTH_SHORT
            ).show()
            quiteTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }


    companion object {
        const val INTENT_KEY_BACK_FROM_HOME = "isBackFromHome"
        fun createIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

}
