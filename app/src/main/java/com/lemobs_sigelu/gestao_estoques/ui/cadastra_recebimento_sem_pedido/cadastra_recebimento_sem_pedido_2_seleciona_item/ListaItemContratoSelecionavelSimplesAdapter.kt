package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import kotlinx.android.synthetic.main.item_adicao_generico.view.*
import kotlinx.android.synthetic.main.item_material_adicao.view.tv_name

class ListaItemContratoSelecionavelSimplesAdapter(private val context: Context,
                                                  private val list: List<ItemEstoque>,
                                                  private val itemClickListener: TwoIntParametersClickListener,
                                                  private val itensJaCadastrados: List<Int>): RecyclerView.Adapter<ListaItemContratoSelecionavelSimplesAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var colorAdicionarItem: Int? = null
    private var colorRemoverItem: Int? = null
    private var colorBranco: Int? = null
    private var colorSecundaryText: Int? = null

    val itensParaAdicao = mutableListOf<ItemEstoque>()
    val itensParaRemocao = mutableListOf<ItemEstoque>()

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
        holder.itemView.btn_add.setOnClickListener {
            itemClickListener.onClick(item.id, position) }


        if(itensParaAdicao.contains(item)){
            holder.itemView.ll_background.setBackgroundColor(colorAdicionarItem!!)
            holder.itemView.tv_name.setTextColor(colorBranco!!)
            holder.itemView.btn_add.background = context.resources.getDrawable(R.drawable.ic_minus_rounded_white)
        }
        else if(itensParaRemocao.contains(item)){
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
        if(itensParaAdicao.contains(item)){
            itensParaAdicao.remove(item)
            notifyItemChanged(position)
        }
        else {
            itensParaAdicao.add(item)
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int){

        val item = this.list[position]

        if(itensParaRemocao.contains(item)){
            itensParaRemocao.remove(item)
            notifyItemChanged(position)
        }
        else{
            itensParaRemocao.add(item)
            notifyItemChanged(position)
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}