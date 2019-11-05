package com.sigelu.logistica.common.domain.model

interface HasEquivalentDTO<T> {

    fun getEquivalentDTO(): T
}