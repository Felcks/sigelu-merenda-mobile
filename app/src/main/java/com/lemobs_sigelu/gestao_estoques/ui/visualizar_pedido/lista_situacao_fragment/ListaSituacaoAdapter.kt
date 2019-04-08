package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_situacao_fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoDePedido
import com.lemobs_sigelu.gestao_estoques.getDataFormatada
import kotlinx.android.synthetic.main.item_situacao_de_pedido.view.*

class ListaSituacaoAdapter (val context: Context,
                            val list: List<SituacaoDePedido>): RecyclerView.Adapter<ListaSituacaoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_situacao_de_pedido, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_data.text = item.data.getDataFormatada()
        holder.itemView.tv_titulo.text = item.nome


        if(position == 0){
            holder.itemView.time_marker.setMarker(context.resources.getDrawable(R.drawable.ic_marker_timeline_inactive))
        }
        else if(position == itemCount - 1){
            holder.itemView.time_marker.setMarker(context.resources.getDrawable(R.drawable.ic_marker_timeline_active))
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}