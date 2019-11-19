package com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_3_confirma

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sigelu.merenda.R
import com.sigelu.merenda.extensions_constants.tracoSeVazio
import kotlinx.android.synthetic.main.item_cr_confirma.view.*
import kotlinx.android.synthetic.main.item_cr_confirma.view.tv_descricao_material
import kotlinx.android.synthetic.main.item_cr_confirma.view.tv_nome_material
import kotlinx.android.synthetic.main.item_cr_confirma.view.tv_unidade_medida

class CRConfirmaAdapter (private val context: Context,
                         private val list: List<ItemRecebimentoDTO>
): RecyclerView.Adapter<CRConfirmaAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_cr_confirma, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]
        holder.itemView.tv_nome_material.text = item.itemEstoque.nomeAlternativo
        holder.itemView.tv_descricao_material.text = item.itemEstoque.descricao
        holder.itemView.tv_unidade_medida.text = item.itemEstoque.unidadeMedida.getNomeESiglaPorExtenso()
        holder.itemView.tv_pedido_total.text = item.quantidadeRecebida.toString()
        holder.itemView.tv_observacao.text = item.observacao.tracoSeVazio()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}