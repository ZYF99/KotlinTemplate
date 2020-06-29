package com.zhangyf.draftbottle.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.zhangyf.draftbottle.BR
import com.zhangyf.draftbottle.util.DialogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import java.util.concurrent.TimeUnit

abstract class BaseFragment<Bind : ViewDataBinding, VM : BaseViewModel>
constructor(
    private val clazz: Class<VM>,
    private val bindingCreator: (LayoutInflater, ViewGroup?) -> Bind
) : Fragment(), BindLife, KodeinAware {

    constructor(clazz: Class<VM>, @LayoutRes layoutRes: Int) : this(clazz, { inflater, group ->
        DataBindingUtil.inflate(inflater, layoutRes, group, false)
    })

    protected open val viewModel: VM by lazy { ViewModelProvider(this).get(clazz) }
    protected lateinit var binding: Bind
    override val kodein by kodein()
    override val compositeDisposable = CompositeDisposable()
    private var currentThrottleTime = 0L

    private var lazyInitDataCompleted = false
    //method

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = bindingCreator.invoke(layoutInflater, container)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventObserver()
        initView()
        initDataAlways()
        if (!viewModel.vmInit) {
            initData()
            viewModel.vmInit = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!lazyInitDataCompleted)
            AndroidSchedulers.mainThread().scheduleDirect(
                {
                    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                        lazyInitData()
                        lazyInitDataCompleted = true
                    }
                },
                500,
                TimeUnit.MILLISECONDS
            )
    }

    private fun initEventObserver() {
        viewModel.apiErrorEvent.handleEvent(requireContext(), this)
        viewModel.dialogEvent.handleEvent(requireContext(), this)
        viewModel.progressDialog.observeNonNull {
            if (it) DialogUtil.showProgressDialog(context)
            else DialogUtil.hideProgressDialog()
        }
    }

    abstract fun initView()
    abstract fun initData()
    open fun lazyInitData() {}
    open fun initDataAlways() {}

    /**
     *  startActivity / Navigation with Throttle by default
     */
    fun startActivity(intent: Intent?, throttleFirst: Boolean = false) {
        if (throttleFirst) startActivity(intent)
        else super.startActivity(intent)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        if (System.currentTimeMillis() - currentThrottleTime < 1000) return
        currentThrottleTime = System.currentTimeMillis()
        super.startActivity(intent, options)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        if (System.currentTimeMillis() - currentThrottleTime < 1000) return
        currentThrottleTime = System.currentTimeMillis()
        super.startActivityForResult(intent, requestCode, options)
    }

    fun navigate(@IdRes resId: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
        if (System.currentTimeMillis() - currentThrottleTime < 1000) return
        currentThrottleTime = System.currentTimeMillis()
        findNavController().navigate(resId, args, navOptions, null)
    }

    //ext
    protected fun <T> LiveData<T>.observe(observer: (T?) -> Unit) where T : Any =
        observe(viewLifecycleOwner, Observer<T> { v -> observer(v) })

    protected fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer {
            if (it != null) observer(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyDisposable()
    }
}
