package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_1_confirma

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ItemContrato
import kotlinx.android.synthetic.main.item_material_cadastrado.view.*

class ListaItemContratoAdapter (val context: Context,
                                val list: List<ItemContrato>):
    RecyclerView.Adapter<ListaItemContratoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_pedido_cadastrado, parent, false)
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
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}