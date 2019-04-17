package com.lemobs_sigelu.gestao_estoques.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.tracoSeVazio
import kotlinx.android.synthetic.main.item_obra.view.*

class ListaObraAdapter(val context: Context,
                       val list: List<*>): RecyclerView.Adapter<ListaObraAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var obraSelecionadaId = 0
    private var itemSelecionadaPos = -1
    private var colorGrayObraSelecionada: Int? = null
    private var colorWhiteObraSelecionada: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_obra, parent, false)
        this.colorGrayObraSelecionada = ContextCompat.getColor(context, android.R.color.darker_gray)
        this.colorWhiteObraSelecionada = ContextCompat.getColor(context, android.R.color.white)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]
        if(item is Obra) {
            holder.itemView.ll_principal.setBackgroundColor(this.colorWhiteObraSelecionada!!)
            holder.itemView.tv_titulo.text = item.codigo
            holder.itemView.tv_1.text = "".tracoSeVazio()
            holder.itemView.tv_2.text = "".tracoSeVazio()
            holder.itemView.tv_3.text = "".tracoSeVazio()
            holder.itemView.tv_4.text = "".tracoSeVazio()

            holder.itemView.ll_principal.setOnClickListener {

                if(itemSelecionadaPos != position) {
                    if(itemSelecionadaPos >= 0)
                        notifyItemChanged(itemSelecionadaPos)
                    itemSelecionadaPos = position

                    obraSelecionadaId = item.id
                    holder.itemView.ll_principal.setBackgroundColor(this.colorGrayObraSelecionada!!)
                }
            }
        }
    }

    fun getObraSelecionadaId(): Int = this.obraSelecionadaId

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}