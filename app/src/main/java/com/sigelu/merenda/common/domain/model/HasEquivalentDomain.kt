package com.sigelu.merenda.common.domain.model

interface HasEquivalentDomain<T>{

    fun getEquivalentDomain(): T
}