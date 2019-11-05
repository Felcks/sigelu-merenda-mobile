package com.sigelu.logistica.ui.estoque

import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sigelu.logistica.App
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.common.domain.model.NucleoQuantidadeDeItemEstoque
import kotlinx.android.synthetic.main.item_material_estoque.view.*

class ListaEstoqueAdapter (val context: Context,
                           val list: List<ItemEstoque>): RecyclerView.Adapter<ListaEstoqueAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var mExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_estoque, parent, false)
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

        holder.itemView.tv_nome_material.text = item.nomeAlternativo
        holder.itemView.tv_descricao_material.text = item.descricao
        holder.itemView.tv_contratado.text = "${item.saldoContrato.toString().replace('.',',')} ${item.unidadeMedida?.sigla}"
        holder.itemView.tv_disponivel.text = "${item.quantidadeDisponivel.toString().replace('.',',')} ${item.unidadeMedida?.sigla}"

        val isExpanded: Boolean = position == mExpandedPosition
        holder.itemView.rv_disponibilidade_nucleos.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.iv_arrow.background = if (isExpanded) ContextCompat.getDrawable(App.instance, R.drawable.ic_arrow_up)
        else ContextCompat.getDrawable(App.instance, R.drawable.ic_arrow_down)

        holder.itemView.ll_disponibilidade_nucleos.isActivated = isExpanded
        holder.itemView.ll_disponibilidade_nucleos.setOnClickListener {

            mExpandedPosition = if (isExpanded) -1 else position
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(holder.itemView.rv_disponibilidade_nucleos)
            }
            notifyItemChanged(position)
        }

        if(item.listaNucleoQuantidadeDeItemEstoque != null) {
            this.startAdapter(holder, item.listaNucleoQuantidadeDeItemEstoque as List<NucleoQuantidadeDeItemEstoque>)
        }
    }

    private fun startAdapter(holder: MyViewHolder, itens: List<NucleoQuantidadeDeItemEstoque>){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.itemView.rv_disponibilidade_nucleos.layoutManager = layoutManager

        val adapter = ListaNucleoQuantidadeDeItemEstoqueAdapter(
            context,
            itens
        )
        holder.itemView.rv_disponibilidade_nucleos.adapter = adapter
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){}
}