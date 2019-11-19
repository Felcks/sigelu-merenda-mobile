package com.sigelu.merenda.ui.estoque

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.NucleoQuantidadeDeItemEstoque
import kotlinx.android.synthetic.main.item_material_nucleo.view.*

class ListaNucleoQuantidadeDeItemEstoqueAdapter(val context: Context,
                                                val list: List<NucleoQuantidadeDeItemEstoque>): RecyclerView.Adapter<ListaNucleoQuantidadeDeItemEstoqueAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_nucleo, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_nucleo_nome.text = item.nome
        holder.itemView.tv_nucleo_quantidade.text = "${item.quantidade.toString().replace('.',',')} ${item.unidadeMedida?.sigla}"
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}