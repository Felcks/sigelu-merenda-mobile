package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.extensions_constants.MASCARA_HORARIO
import com.lemobs_sigelu.gestao_estoques.extensions_constants.esconderTeclado
import com.lemobs_sigelu.gestao_estoques.utils.Mask
import kotlinx.android.synthetic.main.item_cadastrar_quantidade.view.*
import java.text.NumberFormat
import java.util.*

class ListaItemEnvioAdapter (private val context: Context,
                             private val list: List<ItemEnvio>,
                             private val remocaoItemClickListener: TwoIntParametersClickListener
):

    RecyclerView.Adapter<ListaItemEnvioAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val colorItemAprovado = ContextCompat.getColor(App.instance, R.color.fundo_item_aprovado)
    val colorItemReprovado = ContextCompat.getColor(App.instance, R.color.fundo_item_reprovado)
    val colorItemNeutro = ContextCompat.getColor(App.instance, android.R.color.white)

    private var ultimaPosicao = 0
    companion object {
        private var editTexts: Array<EditText?> = arrayOfNulls<EditText?>(50)
        private var mascaras: Array<TextWatcher?> = arrayOfNulls<TextWatcher?>(50)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_cadastrar_quantidade, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_nome_material.text = item.itemEstoque?.nomeAlternativo
        holder.itemView.tv_quantidade_disponivel.text = item.quantidadeUnidade.toString() ?: "0.0"
        holder.itemView.btn_cancel.setOnClickListener {remocaoItemClickListener.onClick(item.id, position)}
        editTexts[position] = holder.itemView.edt_quantidade_fornecida

        val form: NumberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
        form.isGroupingUsed = false
        holder.itemView.edt_quantidade_fornecida.setText(form.format(item.quantidadeRecebida ?: 0.0))


        if(item.quantidadeRecebida ?: 0.0 == 0.0){
            holder.itemView.ll_border.setBackgroundColor(colorItemNeutro)
        }
        else if(item.quantidadeRecebida ?: 0.0 > item.quantidadeUnidade){
            holder.itemView.ll_border.setBackgroundColor(colorItemReprovado)
        }
        else
            holder.itemView.ll_border.setBackgroundColor(colorItemAprovado)

        if(position > ultimaPosicao){
            ultimaPosicao = position
        }

        this.adicionarMascaras(item, holder, position)
    }

    private fun adicionarMascaras(item: ItemEnvio, holder: MyViewHolder, position: Int){

        val mascara = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                var doubleValue = 0.0
                if (s != null) {
                    try {
                        doubleValue = java.lang.Double.parseDouble(s.toString().replace(',', '.'))
                    }
                    catch (e: NumberFormatException) { }

                }
                item.quantidadeRecebida = doubleValue
            }
        }
        mascaras[position] = mascara
        holder.itemView.edt_quantidade_fornecida.addTextChangedListener(mascara)

        holder.itemView.edt_quantidade_fornecida.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            if (event.action != KeyEvent.ACTION_DOWN)
                return@OnKeyListener true

            val sizeOfStringBeforeDel = holder.itemView.edt_quantidade_fornecida.text.length
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (sizeOfStringBeforeDel == 0) {
                    if (position > 0) {
                        editTexts[position - 1]?.requestFocus()
                    }
                    else if (position == 0) {
                        holder.itemView.edt_quantidade_fornecida.clearFocus()
                        holder.itemView.edt_quantidade_fornecida.esconderTeclado()
                    }

                    return@OnKeyListener true
                }
            }
            else if (keyCode == KeyEvent.KEYCODE_ENTER) {

                if (position + 1 <= ultimaPosicao) {
                    notifyItemChanged(position)
                    editTexts[position + 1]?.requestFocus()
                } else {
                    notifyItemChanged(position)
                    holder.itemView.edt_quantidade_fornecida.clearFocus()
                    holder.itemView.edt_quantidade_fornecida.esconderTeclado()
                }

                return@OnKeyListener true
            }

            false
        })
    }

    fun removeItem(position: Int){
        ultimaPosicao = 0
        notifyItemRemoved(position)
    }

    fun getListaValoresItemEnvio(): List<Double>{

        return list.map { it.quantidadeRecebida ?: 0.0 }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}