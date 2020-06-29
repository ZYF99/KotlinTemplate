package com.zhangyf.draftbottle.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.util.DialogUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhangyf.draftbottle.databinding.ActivityMainBinding
import com.zhangyf.draftbottle.manager.sharedpref.SharedPrefModel
import com.zhangyf.draftbottle.ui.base.setupWithNavController

class MainActivity : AppCompatActivity() {

    //纪录第一次点击back时的时间戳（以毫秒为单位的时间）
    private var quiteTime: Long = System.currentTimeMillis()
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //通过dataBinding绑定activity_main布局，并得到Binding文件
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //当界面是首次被打开时，设置BottomNavigation
        //因为若不是首次打开 Activity的 savedInstanceState 参数会有值，所以判断其是否为空即可判断是否是首次启动
        if (savedInstanceState == null) {
            //setUpOnBackPressedDispatcher()
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState!!)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        //通过id得到BottomNavigationView控件
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        // 设置底部BottomNavigationView与33个页面构成的列表绑定在一起
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.top_home,
                R.navigation.top_message,
                R.navigation.top_mine
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment_container,
            intent = intent
        )
        //将MainActivity中的当前NavController赋值为以上
        currentNavController = controller
        //默认选中咨询页面
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
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
}
