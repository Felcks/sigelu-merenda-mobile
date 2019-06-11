package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import kotlinx.android.synthetic.main.item_material_cadastrado.view.*

class ListaItemRecebimentoAdapter(val context: Context,
                                  val list: List<ItemRecebimento>): RecyclerView.Adapter<ListaItemRecebimentoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_cadastrado, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_1.text = item.itemEnvio?.itemEstoque?.nomeAlternativo
        holder.itemView.tv_2.text = item.itemEnvio?.itemEstoque?.descricao
        holder.itemView.tv_3.text = item.itemEnvio?.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
        holder.itemView.tv_4.setText(item.itemEnvio?.quantidadeUnidade.toString())
        holder.itemView.tv_5.setText(item.quantidadeRecebida.toString())

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}