package com.sigelu.merenda.ui.lista_pedidos

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.sigelu.merenda.*
import com.sigelu.merenda.common.domain.model.Pedido2
import com.sigelu.merenda.extensions_constants.SITUACAO_APROVADO_ID
import com.sigelu.merenda.extensions_constants.SITUACAO_PARCIAL_ID
import com.sigelu.merenda.extensions_constants.toDiaMesAno
import com.sigelu.merenda.extensions_constants.tracoSeVazio
import kotlinx.android.synthetic.main.item_pedido.view.*

class ListaPedidoAdapter(private val context: Context,
                         private var list: List<Pedido2>,
                         private val envioOuRecebimentoClickListener: OneIntParameterClickListener,
                         private val visualizarPedidoClickListener: OneIntParameterClickListener): RecyclerView.Adapter<ListaPedidoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val filterList = mutableListOf<Pedido2>()

    private var drawableIconeEnvio: Drawable? = null
    private var drawableIconeRecebimento: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_pedido, parent, false)
        this.drawableIconeEnvio = ContextCompat.getDrawable(context, R.drawable.ic_truck_right)
        this.drawableIconeRecebimento = ContextCompat.getDrawable(context, R.drawable.ic_truck_left)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int =  filterList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.filterList[position]

        holder.itemView.tv_titulo.text = item.getCodigoFormatado()
        holder.itemView.tv_origem.text = item.getOrigemFormatado().tracoSeVazio()
        holder.itemView.tv_destino.text = item.getDestinoFormatado().tracoSeVazio()
        holder.itemView.tv_data_pedido.text = item.getDataPedidoFormatada().tracoSeVazio()
        holder.itemView.tv_data_entrega.text = item.getDataEnvioFormatada().tracoSeVazio()
        holder.itemView.tv_data_ultima_entrega.text = item.getDataRecebimentoFormatada().tracoSeVazio()
        holder.itemView.tv_situacao.text = item.getSituacao()?.situacao_nome

        if(item.getSituacao() != null) {

            val unwrappedDrawable =
                AppCompatResources.getDrawable(context, R.drawable.rounded_button)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(
                wrappedDrawable,
                ContextCompat.getColor(context, item.getSituacao()!!.getColor())
            )
            holder.itemView.rl_situacao.background = wrappedDrawable
            //holder.itemView.rl_situacao.setBackgroundColor(item.situacao!!.getColor())

            if (item.getSituacao()!!.situacao_id == SITUACAO_APROVADO_ID || item.getSituacao()!!.situacao_id == SITUACAO_PARCIAL_ID) {

                //holder.itemView.iv_entrega.visibility = View.VISIBLE
                holder.itemView.iv_entrega.setOnClickListener {
                    envioOuRecebimentoClickListener.onClick(item.id ?: 0)
                }
            } else {
                holder.itemView.iv_entrega.visibility = View.GONE
            }
        }

//        if(item.destino == "Obra" || (item.origem == "Núcleo" && item.origemNome == AppSharedPreferences.getNucleoNome(App.instance))){
//            holder.itemView.iv_entrega.background = drawableIconeEnvio
//        }
//        else{
//            holder.itemView.iv_entrega.background = drawableIconeRecebimento
//
//        }

        holder.itemView.ll_principal.setOnClickListener{visualizarPedidoClickListener.onClick(item.id ?: 0)}
    }

    fun updateAllItens(list: List<Pedido2>){
        filterList.apply {
            clear()
            addAll(list)
        }
        this.list = list
        notifyDataSetChanged()
    }

    fun getPedidoById(id: Int): Pedido2?{
        return this.list.first { it.id == id }
    }

    fun filtraBusca(text: String) {

        filterList.removeAll { true }

        for (item in list) {

            val dataPedido = item.getDataPedido()?.toDiaMesAno()
            if (text == "") {
                filterList.add(item)
            }
            else if(
                item.getCodigo().contains(text, ignoreCase = true) ||
                item.getOrigem().nome.contains(text, ignoreCase = true) == true ||
                item.getOrigem().getTipoNome().contains(text, ignoreCase = true) == true ||
                item.getDestino().nome.contains(text, ignoreCase = true) == true ||
                item.getDestino().getTipoNome().contains(text, ignoreCase = true) == true ||
                item.getSituacao()?.situacao_nome?.contains(text, ignoreCase = true) == true ||
                dataPedido?.contains(text, ignoreCase = true) == true){

                filterList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}