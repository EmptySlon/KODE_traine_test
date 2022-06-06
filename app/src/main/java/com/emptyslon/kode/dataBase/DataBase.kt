package com.emptyslon.kode.dataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.emptyslon.kode.Interface.UserApi

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class DataBase (application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    fun fetchUserList(userApi: UserApi) {
        compositeDisposable.add(userApi.getUserList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers)
            .subscribe({

            },{

            }))

    }




}

