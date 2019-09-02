package com.lemobs_sigelu.gestao_estoques.common.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
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

    val listaItemNucleo: MutableList<ItemNucleo> = mutableListOf()

    val listaItemEstoque: MutableList<ItemEstoque> = mutableListOf()

    var isEdicao: Boolean = false

    fun getTipoPedido(): TipoPedido{

        if(this.origemTipo == "Núcleo" && this.destinoTipo == "Núcleo"){

            if(this.origem == AppSharedPreferences.getNucleoNome(App.instance))
                return TipoPedido.MEU_NUCLEO_PARA_OUTRO_NUCLEO
            else
                return TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO
        }

        if(this.origemTipo == "Fornecedor"){
            return TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO
        }

        return TipoPedido.MEU_NUCLEO_PARA_OBRA
    }

    fun toPedido(): Pedido{

        return Pedido(id ?: 500, codigo, origemTipo, destinoTipo, origemID, destinoID, origem, destino, dataPedido, Date(), Date(), situacao)
    }
}