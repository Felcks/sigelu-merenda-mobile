package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import kotlinx.android.synthetic.main.item_adicao_generico.view.*
import kotlinx.android.synthetic.main.item_obra_adicao.view.*
import kotlinx.android.synthetic.main.item_obra_adicao.view.ll_all
import kotlinx.android.synthetic.main.item_obra_adicao.view.tv_name
import kotlinx.android.synthetic.main.item_obra_adicao.view.ll_background

class SelecionaObraAdapter(val listaObra: List<ObraDTO>): RecyclerView.Adapter<SelecionaObraAdapter.MyViewHolder>() {

    private val listaObraFiltered = mutableListOf<ObraDTO>()
    private val mLayoutInflater: LayoutInflater = App.instance.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    private var colorAdicionarItem: Int? = null
    private var colorBranco: Int? = null
    private var colorSecundaryText: Int? = null

    private var ultimoItemClicado: InformacoesItemClicado? = null

    init {
        this.listaObraFiltered.addAll(listaObra)
        colorAdicionarItem = ContextCompat.getColor(App.instance, R.color.fundo_item_adicionado)
        colorBranco = ContextCompat.getColor(App.instance, android.R.color.white)
        colorSecundaryText = ContextCompat.getColor(App.instance, R.color.secondary_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_obra_adicao, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = listaObraFiltered.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = listaObraFiltered[position]

        holder.itemView.tv_name.text = item.nome

        if(item.getSelecionado()){

            holder.itemView.ll_background.setBackgroundColor(colorAdicionarItem!!)
            holder.itemView.tv_name.setTextColor(colorBranco!!)
        }
        else{

            holder.itemView.ll_background.setBackgroundColor(colorBranco!!)
            holder.itemView.tv_name.setTextColor(colorSecundaryText!!)
        }


        holder.itemView.ll_all.setOnClickListener {
            if(ultimoItemClicado?.posicao != position){

                ultimoItemClicado?.item?.tiraSelecao()
                notifyItemChanged(ultimoItemClicado?.posicao ?: 0)

                item.seleciona()
                notifyItemChanged(position)

                ultimoItemClicado = InformacoesItemClicado(position, item)
            }
        }
    }

    fun getPosicaoSelecionada() = ultimoItemClicado?.posicao ?: -1

    private inner class InformacoesItemClicado(val posicao: Int, val item: ObraDTO)

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}