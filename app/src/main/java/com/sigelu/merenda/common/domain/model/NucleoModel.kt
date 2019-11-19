package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.App
import com.sigelu.merenda.utils.AppSharedPreferences

class NucleoModel {

    fun getNucleoID(): Int{
        return AppSharedPreferences.getNucleoID(App.instance)
    }

    fun getNucleoNome(): String{
        return AppSharedPreferences.getNucleoNome(App.instance)
    }

    fun getNucleo(): Nucleo{
        return Nucleo(getNucleoID(), getNucleoNome(), "")
    }
}