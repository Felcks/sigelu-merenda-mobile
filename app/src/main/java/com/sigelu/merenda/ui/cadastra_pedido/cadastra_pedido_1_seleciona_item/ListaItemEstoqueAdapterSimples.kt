package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.TwoIntParametersClickListener
import kotlinx.android.synthetic.main.item_adicao_generico.view.*
import kotlinx.android.synthetic.main.item_adicao_generico.view.btn_add
import kotlinx.android.synthetic.main.item_adicao_generico.view.tv_name

class ListaItemEstoqueAdapterSimples (val context: Context,
                                      val list: List<ItemEstoqueDTO>,
                                      private val itemClickListener: TwoIntParametersClickListener,
                                      private val itensJaCadastrados: List<Int>): RecyclerView.Adapter<ListaItemEstoqueAdapterSimples.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var colorAdicionarItem: Int? = null
    private var colorRemoverItem: Int? = null
    private var colorBranco: Int? = null
    private var colorSecundaryText: Int? = null

    val itemsParaAdicao = mutableListOf<ItemEstoqueDTO>()
    val itemsParaRemocao = mutableListOf<ItemEstoqueDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        colorAdicionarItem = ContextCompat.getColor(context, R.color.fundo_item_adicionado)
        colorRemoverItem = ContextCompat.getColor(context, R.color.fundo_item_removido)
        colorBranco = ContextCompat.getColor(context, android.R.color.white)
        colorSecundaryText = ContextCompat.getColor(context, R.color.secondary_text)

        val view = mLayoutInflater.inflate(R.layout.item_adicao_generico, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]
        holder.itemView.tv_name.text = item.nome
        holder.itemView.ll_background.setOnClickListener {
            itemClickListener.onClick(item.id, position)
        }

        if(itemsParaAdicao.contains(item)){
            holder.itemView.ll_background.setBackgroundColor(colorAdicionarItem!!)
            holder.itemView.tv_name.setTextColor(colorBranco!!)
            holder.itemView.btn_add.background = context.resources.getDrawable(R.drawable.ic_minus_rounded_white)
        }
        else if(itemsParaRemocao.contains(item)){
            holder.itemView.ll_background.setBackgroundColor(colorRemoverItem!!)
            holder.itemView.tv_name.setTextColor(colorBranco!!)
            holder.itemView.btn_add.background = context.resources.getDrawable(R.drawable.ic_plus_rounded_white)
        }
        else{
            holder.itemView.ll_background.setBackgroundColor(colorBranco!!)
            holder.itemView.tv_name.setTextColor(colorSecundaryText!!)

            if(itensJaCadastrados.contains(item.id)) {
                holder.itemView.btn_add.background = context.resources.getDrawable(R.drawable.ic_minus_rounded)
            }
            else{
                holder.itemView.btn_add.background = context.resources.getDrawable(R.drawable.ic_plus_rounded)
            }
        }
    }

    fun adicionaItem(position: Int){

        val item = this.list[position]
        if(itemsParaAdicao.contains(item)){
            itemsParaAdicao.remove(item)
            notifyItemChanged(position)
        }
        else {
            itemsParaAdicao.add(item)
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int){

        val item = this.list[position]

        if(itemsParaRemocao.contains(item)){
            itemsParaRemocao.remove(item)
            notifyItemChanged(position)
        }
        else{
            itemsParaRemocao.add(item)
            notifyItemChanged(position)
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}