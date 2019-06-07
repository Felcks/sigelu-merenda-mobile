package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
data class PedidoTeste(
    @PrimaryKey val id: Int,
    var codigo: String?,
    val origem: String?,
    val destino: String?)