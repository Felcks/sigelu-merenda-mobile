package com.lemobs_sigelu.gestao_estoques.ui.estoque

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.NucleoQuantidadeDeItemEstoque
import kotlinx.android.synthetic.main.item_material_estoque.view.*

class ListaEstoqueAdapter (val context: Context,
                           val list: List<ItemEstoque>): RecyclerView.Adapter<ListaEstoqueAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_estoque, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_nome_material.text = item.nomeAlternativo
        holder.itemView.tv_descricao_material.text = item.descricao
        holder.itemView.tv_contratado.text = "${item.saldoContrato.toString().replace('.',',')} ${item.unidadeMedida?.sigla}"
        holder.itemView.tv_disponivel.text = "${item.quantidadeDisponivel.toString().replace('.',',')} ${item.unidadeMedida?.sigla}"


        if(item.listaNucleoQuantidadeDeItemEstoque != null) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            holder.itemView.rv_disponibilidade_nucleos.layoutManager = layoutManager

            val adapter = ListaNucleoQuantidadeDeItemEstoqueAdapter(
                context,
                item.listaNucleoQuantidadeDeItemEstoque as List<NucleoQuantidadeDeItemEstoque>
            )
            holder.itemView.rv_disponibilidade_nucleos.adapter = adapter
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}