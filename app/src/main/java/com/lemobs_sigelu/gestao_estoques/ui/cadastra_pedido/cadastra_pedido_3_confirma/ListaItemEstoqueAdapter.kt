package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_confirma

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import kotlinx.android.synthetic.main.item_material_pedido_cadastrado.view.*

class ListaItemEstoqueAdapter (val context: Context,
                               val list: List<MaterialDTO>):
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

        holder.itemView.tv_1.text = item.itemEstoqueDTO.nome
        holder.itemView.tv_2.text = item.itemEstoqueDTO.descricao
        holder.itemView.tv_3.text = item.itemEstoqueDTO.getUnidadeMedidaNomeSiglaPorExtenso()
        holder.itemView.tv_4.text = item.quantidadeRecebida.toString()
        this.adicionarMascaras(item, holder, position)
    }

    fun getListaObservacoes(): List<String>{

        return this.list.map { it.observacao }
    }

    private fun adicionarMascaras(item: MaterialDTO, holder: MyViewHolder, position: Int) {

        val mascara = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                item.observacao = s.toString()
            }
        }
        holder.itemView.edt_observacao.addTextChangedListener(mascara)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}