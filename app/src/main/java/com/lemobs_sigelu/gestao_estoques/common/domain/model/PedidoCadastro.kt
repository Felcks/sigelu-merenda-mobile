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
    @ColumnInfo(name = "tipo_pedido")
    var tipoPedido: TipoPedido?
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var codigo: String? = null

    var origem: String? = null

    var destino: String? = null

    @ColumnInfo(name = "origem_tipo")
    var origemTipo: String? = null

    @ColumnInfo(name = "destino_tipo")
    var destinoTipo: String? = null

    @ColumnInfo(name = "origem_id")
    var origemID: Int? = null

    @ColumnInfo(name = "destino_id")
    var destinoID: Int? = null

    @ColumnInfo(name = "data_pedido")
    var dataPedido: Date? = null

    @ColumnInfo(name = "data_entrega")
    var dataEntrega: Date? = null

    @Embedded
    var situacao: Situacao? = null

    var contratoEstoque: ContratoEstoque? = null

    val listaItemContrato: MutableList<ItemContrato> = mutableListOf()

    val listaItemNucleo: MutableList<ItemNucleo> = mutableListOf()

    val listaItemEstoque: MutableList<ItemEstoque> = mutableListOf()

    var isEdicao: Boolean = false
    fun toPedido(): Pedido{

        return Pedido(id ?: 500, codigo, origemTipo, destinoTipo, origemID, destinoID, origem, destino, dataPedido, Date(), Date(), situacao)
    }

    fun setInformacoes(origem: String,
                       destino: String,
                       origemTipo: String,
                       destinoTipo: String,
                       origemID: Int,
                       destinoID: Int,
                       dataPedido: Date,
                       dataEntrega: Date){

        this.origem = origem
        this.destino = destino
        this.origemTipo = origemTipo
        this.destinoTipo = destinoTipo
        this.origemID = origemID
        this.destinoID = destinoID
        this.dataPedido = dataPedido
        this.dataEntrega = dataEntrega
    }
}