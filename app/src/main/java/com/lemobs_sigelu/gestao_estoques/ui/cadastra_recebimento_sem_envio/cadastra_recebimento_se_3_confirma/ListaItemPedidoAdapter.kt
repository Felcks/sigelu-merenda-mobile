package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_3_confirma

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import kotlinx.android.synthetic.main.item_material_cadastrado_recebimento.view.*

class ListaItemPedidoAdapter(val context: Context,
                             val list: List<ItemPedido>): RecyclerView.Adapter<ListaItemPedidoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_cadastrado_recebimento, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_1.text = item.itemEstoque?.nomeAlternativo
        holder.itemView.tv_2.text = item.itemEstoque?.descricao
        holder.itemView.tv_3.text = item.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
        holder.itemView.tv_4.text = item.quantidadeRecebida.toString()
        holder.itemView.tv_5.text = item.quantidadeUnidade.toString()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}