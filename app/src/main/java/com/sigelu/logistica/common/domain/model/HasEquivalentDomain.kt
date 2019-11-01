package com.sigelu.logistica.common.domain.model

interface HasEquivalentDomain<T>{

    fun getEquivalentDomain(): T
}