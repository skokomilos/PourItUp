package com.example.pouritup

import android.app.Application
import com.example.pouritup.data.db.AppDatabase
import com.example.pouritup.data.network.*
import com.example.pouritup.data.repository.CoctailRepository
import com.example.pouritup.data.repository.CoctailRepositoryImpl
import com.example.pouritup.screens.CustomViewModelFactory
import com.example.pouritup.screens.detail.CustomViewModelFactoryWithArgs
import com.example.pouritup.screens.drink.DrinksViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class CoctailApplication: Application(), KodeinAware {
    override val kodein =  Kodein.lazy{
        //ovo nam omogucuje tj pruza instance contexta, servisa, bilo cega sto je povezano sa Androidom
        import(androidXModule(this@CoctailApplication))

        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().coctailBasicDao()}
        //with singleton u sustini znaci da recimo ovaj ConnectivityInterceptor je interface koji ima svoju implementaiju
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        //nema potreba da ima dve instance vec samo jednu zato kreiramo singleton
        bind() from singleton { CoctailApiService(instance())}
        bind<CoctailNetworkDataSource>() with singleton { CoctailNetworkDataSourceImpl(instance()) }
        bind<CoctailRepository>() with singleton { CoctailRepositoryImpl(instance(), instance()) }
        //no interface ,so we cant directly bind it from provider because it doesnt need to be a singleton, bind() can always return new instance of our factory
        bind() from provider {CustomViewModelFactory(instance())}
        bind() from factory{drinkID : String-> CustomViewModelFactoryWithArgs(drinkID, instance())}
        bind() from factory{ array: Array<String> -> DrinksViewModelFactory(array, instance())}
    }

}