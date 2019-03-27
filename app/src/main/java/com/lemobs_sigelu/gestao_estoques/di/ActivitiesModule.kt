package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    //@ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    @ContributesAndroidInjector(modules = arrayOf(ListaMaterialModule::class))
    abstract fun bindMaterialActivity(): ListaMaterialActivity

}