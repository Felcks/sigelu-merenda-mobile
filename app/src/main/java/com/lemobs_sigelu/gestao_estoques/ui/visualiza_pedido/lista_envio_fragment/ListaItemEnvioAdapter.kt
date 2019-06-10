package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_envio_fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import kotlinx.android.synthetic.main.item_itemenvio.view.*

/**
 * Created by felcks on Jun, 2019
 */
class ListaItemEnvioAdapter (val context: Context,
                             val list: List<ItemEnvio>): RecyclerView.Adapter<ListaItemEnvioAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_itemenvio, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_nome_material.text = item.itemEstoque?.nomeAlternativo
        holder.itemView.tv_codigo.text = item.itemEstoque?.codigo
        holder.itemView.tv_descricao_material.text = item.itemEstoque?.descricao
        holder.itemView.tv_preco.text = item.precoUnidade.toString()
        holder.itemView.tv_material_quantidade.text = "${item.quantidadeUnidade.toString().replace('.',',')} ${item.itemEstoque?.unidadeMedida?.sigla}"
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}