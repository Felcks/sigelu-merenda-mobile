package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.OneIntParameterClickListener
import kotlinx.android.synthetic.main.item_material_adicao.view.*

class ListaItemEnvioSelecionavelSimplesAdapter(val context: Context,
                                               val list: List<ItemEnvio>,
                                               val itemClickListener: OneIntParameterClickListener): RecyclerView.Adapter<ListaItemEnvioSelecionavelSimplesAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_adicao, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_name.text = item.itemEstoque?.nomeAlternativo
        holder.itemView.btn_add.setOnClickListener { itemClickListener.onClick(item.id) }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}