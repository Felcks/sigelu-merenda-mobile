package com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_situacao_fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vipulasri.timelineview.TimelineView
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import com.lemobs_sigelu.gestao_estoques.extensions_constants.getDataFormatada
import kotlinx.android.synthetic.main.item_situacao_de_pedido.view.*

class ListaSituacaoAdapter (val context: Context,
                            val list: List<SituacaoPedido>): RecyclerView.Adapter<ListaSituacaoAdapter.TimeLineViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TimeLineViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_situacao_de_pedido, parent, false)
        return TimeLineViewHolder(view, p1)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_data.text = item.data.getDataFormatada()
        holder.itemView.tv_titulo.text = item.situacao.situacao_nome
        //this.startAdapterMateriais(holder, item)

        if(position == 0){
            holder.itemView.time_marker.setMarker(context.resources.getDrawable(R.drawable.ic_marker_timeline_inactive))
        }
        else if(position == itemCount - 1){
            holder.itemView.time_marker.setMarker(context.resources.getDrawable(R.drawable.ic_marker_timeline_active))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position,itemCount)
    }

    private fun startAdapterMateriais(holder: TimeLineViewHolder, situacaoHistorico: SituacaoHistorico){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.itemView.rv_lista_materiais.layoutManager = layoutManager

        val adapter = ListaMateriaisAdapter(context, situacaoHistorico.materiais)
        holder.itemView.rv_lista_materiais.adapter = adapter
    }

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var mTimelineView: TimelineView = itemView.findViewById(R.id.time_marker) as TimelineView

        init {
            mTimelineView.initLine(viewType)
        }
    }
}