package com.lemobs_sigelu.gestao_estoques.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListClickListener
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import kotlinx.android.synthetic.main.item_pedido.view.*

class ListaPedidoAdapter(val context: Context,
                         val list: List<Pedido>,
                         val entregaClickListener: ListaPedidoActivity,
                         val visualizarPedidoClickListener: ListClickListener
): RecyclerView.Adapter<ListaPedidoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_pedido, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_titulo.text = item.getCodigoFormatado()
        holder.itemView.tv_origem.text = item.origem
        holder.itemView.tv_destino.text = item.destino
        holder.itemView.tv_data_pedido.text = item.getDataPedidoFormatada()
        holder.itemView.tv_data_entrega.text = item.getDataEntregaFormatada()
        holder.itemView.tv_situacao.text = item.situacao.nome
        holder.itemView.rl_situacao.background = context.resources.getDrawable(item.situacao.getColor())

        if(item.situacao.id == 2 || item.situacao.id == 5) {

            holder.itemView.iv_entrega.visibility = View.VISIBLE
            holder.itemView.iv_entrega.setOnClickListener {
                entregaClickListener.entregaPedido(item.id)
            }
        }

        holder.itemView.ll_principal.setOnClickListener{visualizarPedidoClickListener.onClick(item.id)}

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}