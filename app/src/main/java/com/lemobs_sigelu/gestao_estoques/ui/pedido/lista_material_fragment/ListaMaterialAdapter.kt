package com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_material_fragment

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.extensions_constants.tracoSeVazio
import kotlinx.android.synthetic.main.item_material_de_pedido.view.*

class ListaMaterialAdapter (val context: Context,
                            val list: List<ItemPedido>): RecyclerView.Adapter<ListaMaterialAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_de_pedido, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        val quantidadeEnviada = (item.quantidadeUnidade ?: item.quantidadeDisponivel) - item.quantidadeDisponivel

        holder.itemView.tv_nome_material.text = item.itemEstoque?.nomeAlternativo
        holder.itemView.tv_descricao_material.text = item.itemEstoque?.descricao
        holder.itemView.tv_pedido_total.text = "${item.quantidadeUnidade.toString().replace('.',',')} ${item.itemEstoque?.unidadeMedida?.sigla}"
        holder.itemView.tv_material_recebido.text = "${quantidadeEnviada.toString().replace('.',',')} ${item.itemEstoque?.unidadeMedida?.sigla}"
        holder.itemView.tv_material_quantidade.text = "${item.quantidadeDisponivel.toString().replace('.',',')} ${item.itemEstoque?.unidadeMedida?.sigla}"
        holder.itemView.tv_observacao.text =  item.observacao.tracoSeVazio()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}