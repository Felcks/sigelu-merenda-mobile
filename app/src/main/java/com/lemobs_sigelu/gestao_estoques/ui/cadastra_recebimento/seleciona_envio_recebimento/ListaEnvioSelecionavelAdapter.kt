package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_envio_recebimento

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.extensions_constants.toDiaMesAno
import com.lemobs_sigelu.gestao_estoques.extensions_constants.toHoraMinutoSegundo
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_envio_fragment.ListaItemEnvioAdapter
import kotlinx.android.synthetic.main.item_envio_selecionavel.view.*

/**
 * Created by felcks on Jun, 2019
 */
class ListaEnvioSelecionavelAdapter(val context: Context,
                                    val list: List<Envio>): RecyclerView.Adapter<ListaEnvioSelecionavelAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val colorItemEntregue = ContextCompat.getColor(App.instance, R.color.envio_entregue)
    val colorItemSelecionado = ContextCompat.getColor(App.instance, R.color.envio_selecionado)
    val colorItemNaoSelecionado = ContextCompat.getColor(App.instance, R.color.envio_nao_selecionado)

    var layoutSelecionado: CardView? = null
    var posicaoSelecionada: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_envio_selecionavel, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_nome.text = item.responsavel
        holder.itemView.tv_titulo.text = item.codigo
        holder.itemView.tv_saida.text = "${item.dataSaida?.toDiaMesAno()} às ${item.dataSaida?.toHoraMinutoSegundo()}"
        holder.itemView.tv_situacao_atual.text = item.situacao

        if(item.isEntregue){
            holder.itemView.ll_all.setBackgroundColor(colorItemEntregue)
            holder.itemView.ll_content.setBackgroundColor(colorItemEntregue)
        }

        holder.itemView.ll_clickable_layout.setOnClickListener {

            if (!holder.isExpanded) {
                holder.itemView.rv_itens_envio.visibility = View.VISIBLE
                holder.itemView.arrow.background = ContextCompat.getDrawable(App.instance, R.drawable.ic_arrow_up)
                holder.isExpanded = true
            } else {
                holder.itemView.rv_itens_envio.visibility = View.GONE
                holder.itemView.arrow.background = ContextCompat.getDrawable(App.instance, R.drawable.ic_arrow_down)
                holder.isExpanded = false
            }
        }

        holder.itemView.ll_all.setOnClickListener {

            if(!item.isEntregue) {
                layoutSelecionado?.setBackgroundColor(colorItemNaoSelecionado)
                holder.itemView.ll_all.setBackgroundColor(colorItemSelecionado)
                posicaoSelecionada = position
                layoutSelecionado = holder.itemView.ll_all
            }
            else{
                Toast.makeText(context, "Envio já entregue", Toast.LENGTH_SHORT).show()
            }
        }

        this.startAdapter(holder, item.itens)
    }

    private fun startAdapter(holder: MyViewHolder, itens: List<ItemEnvio>){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.itemView.rv_itens_envio.layoutManager = layoutManager

        val adapter = ListaItemEnvioAdapter(App.instance, itens)
        holder.itemView.rv_itens_envio.adapter = adapter
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var isExpanded = false
    }
}