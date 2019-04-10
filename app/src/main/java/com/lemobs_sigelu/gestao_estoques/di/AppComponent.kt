package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    AppModule::class,
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class))

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun build(): AppComponent

    }

    fun inject(app: App)
}