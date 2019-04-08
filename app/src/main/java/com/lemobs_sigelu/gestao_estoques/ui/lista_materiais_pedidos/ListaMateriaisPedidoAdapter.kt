package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import kotlinx.android.synthetic.main.item_material_entrega.view.*

class ListaMateriaisPedidoAdapter(val context: Context,
                                  val list: List<MaterialDePedido>): RecyclerView.Adapter<ListaMateriaisPedidoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_entrega, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.itemView.tv_nome_material.text = item.nome
        holder.itemView.tv_pedido_total.text = "${item.contratado.toString().replace('.', ',')} ${item.unidadeMedida.sigla}"
        holder.itemView.tv_material_recebido.text = "${item.recebido.toString().replace('.', ',')} ${item.unidadeMedida.sigla}"
        holder.itemView.tv_unidade_medida.text = item.unidadeMedida.sigla

        holder.itemView.edt_quantidade_entregue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(holder.itemView.edt_quantidade_entregue.text.isNotEmpty()) {
                    val valorEntregue = holder.itemView.edt_quantidade_entregue.text.toString().replace(',', '.').toDouble()
                    item.entregue = valorEntregue

                    if (valorEntregue + item.recebido < item.contratado) {
                        holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(R.color.quantidade_aceita))
                    } else {
                        holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(R.color.quantidade_rejeitada))
                    }
                }
                else{
                    holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(android.R.color.darker_gray))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}