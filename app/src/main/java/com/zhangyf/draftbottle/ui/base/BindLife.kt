package com.zhangyf.draftbottle.ui.base

import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

interface BindLife {
    val compositeDisposable: CompositeDisposable

    fun Disposable.bindLife() = addDisposable(this)

    fun Single<*>.bindLife() = subscribe({ }, { Timber.e(it, "Single error") }).bindLife()

    fun Observable<*>.bindLife() =
        subscribe({ }, { Timber.e(it, "Observable error") }).bindLife()

    fun Completable.bindLife() =
        subscribe({ }, { Timber.e(it, "Completable error") }).bindLife()

    fun Maybe<*>.bindLife() = subscribe({}, { Timber.e(it, "Maybe error") }).bindLife()

    fun Flowable<*>.bindLife() = subscribe({ }, { Timber.e(it, "Flowable  error") }).bindLife()

    fun addDisposable(disposable: Disposable): Disposable {
        compositeDisposable.add(disposable)
        return disposable
    }

    fun removeDisposable(disposable: Disposable?) {
        if (disposable != null)
            compositeDisposable.remove(disposable)
    }

    fun destroyDisposable() = compositeDisposable.clear()
}