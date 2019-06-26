package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.utils.esconderTeclado
import kotlinx.android.synthetic.main.item_material_entrega.view.*

class EntregaMateriaisPedidoAdapter(val context: Context,
                                    val list: List<ItemPedido>): RecyclerView.Adapter<EntregaMateriaisPedidoAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var hasFocus = false
    var ultimaPosicao = 0

    companion object {
        private var editTexts: Array<EditText?> = arrayOfNulls<EditText?>(50)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_entrega, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]
        item.entregue = 0.0
        editTexts[position] = holder.itemView.edt_quantidade_entregue

//        holder.itemView.tv_nome_material.text = item.base.nome
//        holder.itemView.tv_pedido_total.text = "${item.contratado.toString().replace('.', ',')} ${item.base.unidadeMedida.sigla}"
//        holder.itemView.tv_material_recebido.text = "${item.recebido.toString().replace('.', ',')} ${item.base.unidadeMedida.sigla}"
//        holder.itemView.tv_material_saldo.text = "${(item.contratado - item.recebido).toString().replace('.', ',')} ${item.base.unidadeMedida.sigla}"
//        holder.itemView.tv_unidade_medida.text = item.base.unidadeMedida.sigla
        holder.itemView.edt_quantidade_entregue.setText("")
        holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(android.R.color.darker_gray))


        holder.itemView.edt_quantidade_entregue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        holder.itemView.edt_quantidade_entregue.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

            if(hasFocus){
                this.hasFocus = true
            }
            else {
                this.hasFocus = false
                if (holder.itemView.edt_quantidade_entregue.text.isNotEmpty()) {
                    val valorEntregue =
                        holder.itemView.edt_quantidade_entregue.text.toString().replace(',', '.').toDouble()
                    item.entregue = valorEntregue

//                    if (valorEntregue + item.recebido <= item.contratado) {
//                        holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(R.color.quantidade_aceita))
//                    } else {
//                        holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(R.color.quantidade_rejeitada))
//                    }
                } else {
                    item.entregue = 0.0
                    holder.itemView.ll_borda.setBackgroundColor(context.resources.getColor(android.R.color.darker_gray))
                }
            }
        }

        holder.itemView.edt_quantidade_entregue.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            if (event.action != KeyEvent.ACTION_DOWN)
                return@OnKeyListener true

            val sizeOfStringBeforeDel = holder.itemView.edt_quantidade_entregue.text.length

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (sizeOfStringBeforeDel == 0) {
                    if (position > 0) {
                        editTexts[position - 1]?.requestFocus()
                    }
                    else if (position == 0) {
                        holder.itemView.edt_quantidade_entregue.clearFocus()
                        holder.itemView.edt_quantidade_entregue.esconderTeclado()
                    }

                    return@OnKeyListener true
                }
            }
            else if (keyCode == KeyEvent.KEYCODE_ENTER) {

                if (position + 1 <= ultimaPosicao) {
                    editTexts[position + 1]?.requestFocus()
                } else {
                    holder.itemView.edt_quantidade_entregue.clearFocus()
                    holder.itemView.edt_quantidade_entregue.esconderTeclado()
                }

                return@OnKeyListener true
            }
            false
        })

        if(position > ultimaPosicao){
            ultimaPosicao = position
        }
    }

    fun updateAllItens(){
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}