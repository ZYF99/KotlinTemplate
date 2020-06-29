package com.zhangyf.draftbottle.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.zhangyf.draftbottle.ui.base.BaseActivity
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.databinding.ActivitySingleBinding
import java.io.Serializable

class ContentActivity : BaseActivity() {

    private val acViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivitySingleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single)
        intent.extras.let {
            if(it == null){
                findNavController(R.id.nav_host_fragment).navigate(
                    R.id.splash_fragment,
                    null,
                    navOptions { popUpTo(R.id.bridgeFragment) { inclusive = true } })
            }else{
                (it.getSerializable(INTENT_EXTRA_KEY_EXTRA_DES) as? Destination)?.let { des ->
/*                if (des != Destination.ArticleDetail || des != Destination.ScCompanySearch) {
                    swipeBackLayout.setEnableGesture(false)
                }*/
                    findNavController(R.id.nav_host_fragment).navigate(
                        des.id,
                        it.getBundle(INTENT_EXTRA_KEY_BUNDLE),
                        navOptions { popUpTo(R.id.bridgeFragment) { inclusive = true } }
                    )
                }

                (it.getInt(INTENT_EXTRA_KEY_DES_ID)).let { desId ->
                    if (desId == 0) return@let
                    findNavController(R.id.nav_host_fragment).navigate(
                        desId,
                        null,
                        navOptions { popUpTo(R.id.bridgeFragment) { inclusive = true } })
                }
            }

        }
    }



    companion object {
        private const val INTENT_EXTRA_KEY_EXTRA_DES = "extra_des"
        private const val INTENT_EXTRA_KEY_DES_ID = "extra_des_id"
        private const val INTENT_EXTRA_KEY_BUNDLE = "extra_args_bundle"
        private const val BUNDLE_KEY = "extra_args"

        fun createIntent(
            context: Context,
            des: Destination,
            s: Serializable? = null,
            bundle: Bundle? = null
        ): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra(INTENT_EXTRA_KEY_EXTRA_DES, des)
                .putExtra(INTENT_EXTRA_KEY_BUNDLE, bundle ?: bundleOf(BUNDLE_KEY to s))
        }

        // desId may not contains in the navGraph
        fun createIntentUnsafe(context: Context, @IdRes desId: Int): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra(INTENT_EXTRA_KEY_DES_ID, desId)
        }
    }

    enum class Destination(@IdRes val id: Int) {
        Splash(R.id.splash_fragment)
        //Setting(R.id.navigation_setting),
    }
}
