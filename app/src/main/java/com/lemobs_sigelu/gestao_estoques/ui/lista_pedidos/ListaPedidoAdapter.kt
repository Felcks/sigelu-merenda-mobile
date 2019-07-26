package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_APROVADO_ID
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_PARCIAL_ID
import com.lemobs_sigelu.gestao_estoques.extensions_constants.tracoSeVazio
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import kotlinx.android.synthetic.main.item_pedido.view.*

class ListaPedidoAdapter(val context: Context,
                         var list: List<Pedido>,
                         private val envioOuRecebimentoClickListener: OneIntParameterClickListener,
                         private val visualizarPedidoClickListener: OneIntParameterClickListener
): RecyclerView.Adapter<ListaPedidoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var drawableIconeEnvio: Drawable? = null
    private var drawableIconeRecebimento: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_pedido, parent, false)
        this.drawableIconeEnvio = ContextCompat.getDrawable(context, R.drawable.ic_truck_right)
        this.drawableIconeRecebimento = ContextCompat.getDrawable(context, R.drawable.ic_truck_left)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_titulo.text = item.getCodigoFormatado()
        holder.itemView.tv_origem.text = item.getOrigemFormatado().tracoSeVazio()
        holder.itemView.tv_destino.text = item.getDestinoFormatado().tracoSeVazio()
        holder.itemView.tv_data_pedido.text = item.getDataPedidoFormatada().tracoSeVazio()
        holder.itemView.tv_data_entrega.text = item.getDataEnvioFormatada().tracoSeVazio()
        holder.itemView.tv_data_ultima_entrega.text = item.getDataRecebimentoFormatada().tracoSeVazio()
        holder.itemView.tv_situacao.text = item.situacao?.situacao_nome
        holder.itemView.rl_situacao.background = context.resources.getDrawable(item.situacao!!.getColor())

        if(item.situacao!!.situacao_id == SITUACAO_APROVADO_ID || item.situacao!!.situacao_id == SITUACAO_PARCIAL_ID) {

            holder.itemView.iv_entrega.visibility = View.VISIBLE
            holder.itemView.iv_entrega.setOnClickListener {
                envioOuRecebimentoClickListener.onClick(item.id)
            }
        }
        else{
            holder.itemView.iv_entrega.visibility = View.GONE
        }

        if(item.destino == "Obra" || (item.origem == "Núcleo" && item.origemNome == AppSharedPreferences.getNucleoNome(App.instance))){
            holder.itemView.iv_entrega.background = drawableIconeEnvio
        }
        else{
            holder.itemView.iv_entrega.background = drawableIconeRecebimento

        }

        holder.itemView.ll_principal.setOnClickListener{visualizarPedidoClickListener.onClick(item.id)}
    }

    fun updateAllItens(list: List<Pedido>){
        this.list = list
        notifyDataSetChanged()
    }

    fun getPedidoById(id: Int): Pedido?{
        return this.list.first { it.id == id }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}