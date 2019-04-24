package com.lemobs_sigelu.gestao_estoques.common.domain.model

interface HasEquivalentDTO<T> {

    fun getEquivalentDTO(): T
}