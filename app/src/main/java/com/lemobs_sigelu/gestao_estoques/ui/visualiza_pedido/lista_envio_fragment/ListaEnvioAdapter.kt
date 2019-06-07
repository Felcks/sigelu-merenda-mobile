package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_envio_fragment

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vipulasri.timelineview.TimelineView
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.toDiaMesAno
import com.lemobs_sigelu.gestao_estoques.toHoraMinutoSegundo
import kotlinx.android.synthetic.main.item_envio.view.*

/**
 * Created by felcks on Jun, 2019
 */
class ListaEnvioAdapter(val context: Context,
                        val list: List<Envio>): RecyclerView.Adapter<ListaEnvioAdapter.TimeLineViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TimeLineViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_envio, parent, false)
        val mvh = TimeLineViewHolder(view, p1)
        return mvh
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position,itemCount)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_nome.text = item.responsavel
        holder.itemView.tv_titulo.text = item.codigo
        holder.itemView.tv_saida.text = "${item.dataSaida?.toDiaMesAno()} às ${item.dataSaida?.toHoraMinutoSegundo()}"
        holder.itemView.tv_situacao_atual.text = item.situacao

        holder.itemView.ll_clickable_layout.setOnClickListener {

            if(!holder.isExpanded){
                holder.itemView.rv_itens_envio.visibility = View.VISIBLE
                holder.itemView.arrow.background = ContextCompat.getDrawable(App.instance, R.drawable.ic_arrow_up)
                holder.isExpanded = true
            }
            else{
                holder.itemView.rv_itens_envio.visibility = View.GONE
                holder.itemView.arrow.background = ContextCompat.getDrawable(App.instance, R.drawable.ic_arrow_down)
                holder.isExpanded = false
            }
        }

        this.startAdapter(holder, item.itens)
    }

    private fun startAdapter(holder: TimeLineViewHolder, itens: List<ItemEnvio>){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.itemView.rv_itens_envio.layoutManager = layoutManager

        val adapter = ListaItemEnvioAdapter(App.instance, itens)
        holder.itemView.rv_itens_envio.adapter = adapter
    }

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        var isExpanded = false
        private var mTimelineView: TimelineView = itemView.findViewById(R.id.time_marker) as TimelineView
        init {
            mTimelineView.initLine(viewType)
        }
    }
}