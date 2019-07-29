package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "pedido_cadastro")
data class PedidoCadastro(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    var codigo: String?,

    var origem: String?,

    var destino: String?,

    @ColumnInfo(name = "origem_tipo")
    var origemTipo: String?,

    @ColumnInfo(name = "destino_tipo")
    var destinoTipo: String?,

    @ColumnInfo(name = "origem_id")
    var origemID: Int?,

    @ColumnInfo(name = "destino_id")
    var destinoID: Int?,

    @ColumnInfo(name = "data_pedido")
    var dataPedido: Date? = null,

    @ColumnInfo(name = "data_entrega")
    var dataEntrega: Date? = null,

    @Embedded
    var situacao: Situacao?){

    var contratoEstoque: ContratoEstoque? = null

    val listaItemContrato: MutableList<ItemContrato> = mutableListOf()

    fun getTipoPedido(): TipoPedido{

        if(this.origemTipo == "Núcleo" && this.destinoTipo == "Núcleo"){
            return TipoPedido.MEU_NUCLEO_PARA_OUTRO_NUCLEO
        }

        if(this.origemTipo == "Núcleo" && this.destinoTipo == "Obra"){
            return TipoPedido.MEU_NUCLEO_PARA_OBRA
        }

        if(this.origemTipo == "Fornecedor"){
            return TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO
        }

        return TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO
    }

    fun toPedido(): Pedido{

        return Pedido(id ?: 500, codigo, origemTipo, destinoTipo, origemID, destinoID, origem, destino, dataPedido, Date(), Date(), situacao)
    }
}