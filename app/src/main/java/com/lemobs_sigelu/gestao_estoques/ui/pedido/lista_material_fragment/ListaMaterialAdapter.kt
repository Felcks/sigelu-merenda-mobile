package com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_material_fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
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


        holder.itemView.tv_nome_material.text = item.itemEstoque?.nomeAlternativo
        holder.itemView.tv_descricao_material.text = item.itemEstoque?.descricao
        holder.itemView.tv_material_quantidade.text = "${item.quantidadeUnidade.toString().replace('.',',')} ${item.itemEstoque?.unidadeMedida?.sigla}"
//        holder.itemView.tv_pedido_total.text = "${item.contratado.toString().replace('.', ',')} ${item.base.unidadeMedida.sigla}"
//        holder.itemView.tv_material_recebido.text = "${item.recebido.toString().replace('.', ',')} ${item.base.unidadeMedida.sigla}"
//        holder.itemView.tv_material_saldo.text = "${(item.contratado - item.recebido).toString().replace('.', ',')} ${item.base.unidadeMedida.sigla}"
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}