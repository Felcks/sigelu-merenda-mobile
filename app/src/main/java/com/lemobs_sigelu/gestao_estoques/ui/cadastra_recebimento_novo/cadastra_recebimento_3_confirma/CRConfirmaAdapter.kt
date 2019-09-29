package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_3_confirma

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lemobs_sigelu.gestao_estoques.R
import kotlinx.android.synthetic.main.item_cr_confirma.view.*

class CRConfirmaAdapter (private val context: Context,
                         private val list: List<ItemRecebimentoDTO>
): RecyclerView.Adapter<CRConfirmaAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_cr_confirma, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]
        holder.itemView.tv_1.text = item.itemEstoque.nomeAlternativo
        holder.itemView.tv_2.text = item.itemEstoque.descricao
        holder.itemView.tv_3.text = item.itemEstoque.unidadeMedida.getNomeESiglaPorExtenso()
        holder.itemView.tv_4.text = item.quantidadeRecebida.toString()
        holder.itemView.edt_observacao.setText(item.observacao)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}