package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_4_confirma

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ItemRecebimento
import kotlinx.android.synthetic.main.item_material_cadastrado.view.*

class ListaItemRecebimentoAdapter(val context: Context,
                                  val list: List<ItemRecebimento>): RecyclerView.Adapter<ListaItemRecebimentoAdapter.MyViewHolder>() {

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

        holder.itemView.tv_1.text = item.itemEnvio?.itemEstoque?.nomeAlternativo
        holder.itemView.tv_2.text = item.itemEnvio?.itemEstoque?.descricao
        holder.itemView.tv_3.text = item.itemEnvio?.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
        holder.itemView.tv_4.text = item.quantidadeRecebida.toString()
        holder.itemView.tv_5.text = item.itemEnvio?.quantidadeUnidade.toString()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}