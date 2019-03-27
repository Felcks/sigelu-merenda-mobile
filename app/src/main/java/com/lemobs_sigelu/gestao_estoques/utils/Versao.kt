package com.lemobs_sigelu.gestao_estoques.utils

import com.lemobs_sigelu.gestao_estoques.BuildConfig

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