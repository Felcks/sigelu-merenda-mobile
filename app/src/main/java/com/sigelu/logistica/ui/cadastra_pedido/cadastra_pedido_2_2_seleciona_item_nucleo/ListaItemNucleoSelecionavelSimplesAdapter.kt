package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ItemNucleo
import com.sigelu.logistica.common.domain.model.TwoIntParametersClickListener
import kotlinx.android.synthetic.main.item_adicao_generico.view.*

class ListaItemNucleoSelecionavelSimplesAdapter (private val context: Context,
                                                 private val list: List<ItemNucleo>,
                                                 private val itemClickListener: TwoIntParametersClickListener,
                                                 private val itensJaCadastrados: List<Int>): RecyclerView.Adapter<ListaItemNucleoSelecionavelSimplesAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var colorAdicionarItem: Int? = null
    private var colorRemoverItem: Int? = null
    private var colorBranco: Int? = null
    private var colorSecundaryText: Int? = null

    val itemsParaAdicao = mutableListOf<ItemNucleo>()
    val itemsParaRemocao = mutableListOf<ItemNucleo>()

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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_name.text = item.nomeAlternativo
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