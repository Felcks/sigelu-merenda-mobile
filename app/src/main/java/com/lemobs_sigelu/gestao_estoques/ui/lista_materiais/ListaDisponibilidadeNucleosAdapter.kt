package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.NucleoComMaterial
import kotlinx.android.synthetic.main.item_material_nucleo.view.*

class ListaDisponibilidadeNucleosAdapter (val context: Context,
                                          val list: List<NucleoComMaterial>): RecyclerView.Adapter<ListaDisponibilidadeNucleosAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_nucleo, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list.get(position)

        holder.itemView.tv_nucleo_nome.text = item.nome
        holder.itemView.tv_nucleo_quantidade.text = "${item.quantidade} ${item.siglaUnidadeMedida}"
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}