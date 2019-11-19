package com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_4_confirma

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.ItemEstoque
import com.sigelu.merenda.extensions_constants.tracoSeVazio
import kotlinx.android.synthetic.main.item_ce_confirma.view.*

class ListaItemEstoqueAdapter (val context: Context,
                               val list: List<ItemEstoque>):
    RecyclerView.Adapter<ListaItemEstoqueAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_ce_confirma, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_nome_material.text = item.nomeAlternativo
        holder.itemView.tv_descricao_material.text = item.descricao
        holder.itemView.tv_unidade_medida.text = item.unidadeMedida?.getNomeESiglaPorExtenso()
        holder.itemView.tv_quantidade_enviada.text = item.quantidadeRecebida.toString()
        holder.itemView.tv_observacao.text = item.observacao.tracoSeVazio()
    }

    fun getListaObservacoes(): List<String>{
        return this.list.map { it.observacao }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}