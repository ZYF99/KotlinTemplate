package com.zhangyf.draftbottle.ui.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.util.DialogUtil
import com.zhangyf.draftbottle.util.autoProgressDialog
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseViewModel(app: Application) : AndroidViewModel(app),
    LifecycleObserver,
    BindLife,
    KodeinAware {

    override val kodein by kodein()
    override val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    val apiErrorEvent = ApiErrorLiveEvent()
    val dialogEvent = DialogLiveEvent()

    val progressDialog = MutableLiveData(false)

    var vmInit = false

    protected fun <T> Single<T>.catchApiError(): Single<T> = catchApiError(apiErrorEvent)
    protected fun <T> Observable<T>.catchApiError(): Observable<T> = catchApiError(apiErrorEvent)
    protected fun Completable.catchApiError(): Completable = catchApiError(apiErrorEvent)

    protected fun <T> Single<T>.autoProgressDialog(): Single<T> = autoProgressDialog(progressDialog)
    protected fun Completable.autoProgressDialog(): Completable = autoProgressDialog(progressDialog)

    //is this necessary?
    protected fun showDialog(
        @StringRes title: Int = R.string.dialog_title,
        @StringRes msg: Int = R.string.dialog_msg,
        @StringRes positiveButtonText: Int = R.string.dialog_ok,
        @StringRes negativeButtonText: Int = R.string.dialog_cancel,
        @DialogUtil.ButtonType positiveButton: Long = DialogUtil.BUTTON_TYPE_OK,
        @DialogUtil.ButtonType negativeButton: Long = DialogUtil.BUTTON_TYPE_CANCEL
    ) {
        dialogEvent.value = Event(
            DialogEventData(
                title,
                msg,
                positiveButtonText,
                negativeButtonText,
                positiveButton,
                negativeButton
            )
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}