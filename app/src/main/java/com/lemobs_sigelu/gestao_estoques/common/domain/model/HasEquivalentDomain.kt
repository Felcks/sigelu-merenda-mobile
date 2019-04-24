package com.lemobs_sigelu.gestao_estoques.common.domain.model

interface HasEquivalentDomain<T>{

    fun getEquivalentDomain(): T
}