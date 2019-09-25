package com.lemobs_sigelu.gestao_estoques

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.jakewharton.threetenabp.AndroidThreeTen
import com.lemobs_sigelu.gestao_estoques.di.DaggerAppComponent
import com.lemobs_sigelu.gestao_estoques.di.DependencyModules
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        startKoin {

            androidContext(this@App)
            modules(listOf(DependencyModules.appModule))
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }
}