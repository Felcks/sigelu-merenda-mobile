package com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_situacao_fragment

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDeSituacao
import kotlinx.android.synthetic.main.item_material_situacao.view.*

class ListaMateriaisAdapter (val context: Context,
                             val list: List<MaterialDeSituacao>): RecyclerView.Adapter<ListaMateriaisAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_situacao, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_material_nome.text = item.base.nomeAlternativo
        holder.itemView.tv_material_unidade.text = "(${item.base.unidadeMedida?.sigla})"
        holder.itemView.tv_material_recebido.text = item.recebido.toString()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}