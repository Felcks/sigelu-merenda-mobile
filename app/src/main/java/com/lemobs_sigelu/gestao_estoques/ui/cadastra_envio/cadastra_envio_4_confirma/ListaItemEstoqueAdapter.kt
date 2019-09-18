package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import kotlinx.android.synthetic.main.item_material_pedido_cadastrado.view.*

class ListaItemEstoqueAdapter (val context: Context,
                               val list: List<ItemEstoque>):
    RecyclerView.Adapter<ListaItemEstoqueAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_pedido_cadastrado, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        holder.itemView.tv_1.text = item.nomeAlternativo
        holder.itemView.tv_2.text = item.descricao
        holder.itemView.tv_3.text = item.unidadeMedida?.getNomeESiglaPorExtenso()
        holder.itemView.tv_4.text = item.quantidadeRecebida.toString()
        this.adicionarMascaras(item, holder, position)
    }

    private fun adicionarMascaras(item: ItemEstoque, holder: MyViewHolder, position: Int) {

        val mascara = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                item.observacao = s.toString()
            }
        }
        holder.itemView.edt_observacao.addTextChangedListener(mascara)
    }

    fun getListaObservacoes(): List<String>{
        return this.list.map { it.observacao }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}