package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_confirma

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.logistica.R
import com.sigelu.logistica.extensions_constants.tracoSeVazio
import kotlinx.android.synthetic.main.item_cp_confirma.view.*

class ListaItemEstoqueAdapter (val context: Context,
                               val list: List<MaterialDTO>):
    RecyclerView.Adapter<ListaItemEstoqueAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_cp_confirma, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_nome_material.text = item.itemEstoqueDTO.nome
        holder.itemView.tv_descricao_material.text = item.itemEstoqueDTO.descricao
        holder.itemView.tv_unidade_medida.text = item.itemEstoqueDTO.getUnidadeMedidaNomeSiglaPorExtenso()
        holder.itemView.tv_pedido_total.text = item.quantidadeRecebida.toString()
        holder.itemView.tv_observacao.text = item.observacao.tracoSeVazio()
    }

    fun getListaObservacoes(): List<String>{

        return this.list.map { it.observacao }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}