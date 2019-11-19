package com.sigelu.merenda.common.domain.model

interface HasEquivalentDTO<T> {

    fun getEquivalentDTO(): T
}