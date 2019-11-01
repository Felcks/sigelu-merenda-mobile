package com.sigelu.logistica.utils

import com.sigelu.logistica.BuildConfig

class Versao {

    companion object {

        fun getURL(): String {

            return BuildConfig.BASE_URL
        }

        fun getFilesURL(): String {

            return BuildConfig.FOTO_URL
        }

        fun getURLAccount(): String {

            return BuildConfig.ACCOUNT_URL
        }

    }
}