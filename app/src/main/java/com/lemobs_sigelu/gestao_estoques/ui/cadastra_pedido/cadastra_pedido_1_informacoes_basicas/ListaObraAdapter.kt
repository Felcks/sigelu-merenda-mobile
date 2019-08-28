package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.extensions_constants.tracoSeVazio
import kotlinx.android.synthetic.main.item_obra.view.*

class ListaObraAdapter(val context: Context,
                       val list: List<*>): RecyclerView.Adapter<ListaObraAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var obraSelecionadaId = 0
    private var itemSelecionadaPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_obra, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]
        if(item is Obra) {
            holder.itemView.iv_done.visibility = View.INVISIBLE
            holder.itemView.tv_titulo.text = item.getTitulo()
            holder.itemView.tv_1.text = item.distancia.tracoSeVazio()
            holder.itemView.tv_2.text = item.conclusaoPrevista.tracoSeVazio()
            holder.itemView.tv_3.text = item.situacao.tracoSeVazio()
            holder.itemView.tv_4.text = item.endereco.tracoSeVazio()
            holder.itemView.tv_situacao.text = item.getStatusDeConclusao()
            holder.itemView.rl_situacao.setBackgroundColor(ContextCompat.getColor(context, item.getColor()))

            holder.itemView.ll_principal.setOnClickListener {

                if(itemSelecionadaPos != position) {
                    if(itemSelecionadaPos >= 0)
                        notifyItemChanged(itemSelecionadaPos)
                    itemSelecionadaPos = position

                    obraSelecionadaId = item.id
                    holder.itemView.iv_done.visibility = View.VISIBLE
                }
            }
        }
    }

    fun getObraSelecionadaId(): Int = this.obraSelecionadaId

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}