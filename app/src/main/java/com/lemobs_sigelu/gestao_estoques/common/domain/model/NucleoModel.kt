package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences

class NucleoModel {

    fun getNucleoID(): Int{
        return AppSharedPreferences.getNucleoID(App.instance)
    }

    fun getNucleoNome(): String{
        return AppSharedPreferences.getNucleoNome(App.instance)
    }
}