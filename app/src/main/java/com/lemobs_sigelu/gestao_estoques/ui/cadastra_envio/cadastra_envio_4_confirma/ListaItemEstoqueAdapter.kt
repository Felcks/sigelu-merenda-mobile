package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import kotlinx.android.synthetic.main.item_material_cadastrado.view.*

class ListaItemEstoqueAdapter (val context: Context,
                               val list: List<ItemEstoque>):
    RecyclerView.Adapter<ListaItemEstoqueAdapter.MyViewHolder>() {

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

        holder.itemView.tv_1.text = item.nomeAlternativo
        holder.itemView.tv_2.text = item.descricao
        holder.itemView.tv_3.text = item.unidadeMedida?.getNomeESiglaPorExtenso()
        holder.itemView.tv_4.text = item.quantidadeRecebida.toString()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}