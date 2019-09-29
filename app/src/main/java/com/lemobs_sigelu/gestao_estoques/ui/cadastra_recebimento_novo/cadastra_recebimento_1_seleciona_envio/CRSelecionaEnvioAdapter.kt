package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_1_seleciona_envio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import kotlinx.android.synthetic.main.item_cr_envio.view.*

class CRSelecionaEnvioAdapter (val context: Context,
                               val list: List<EnvioDTO>,
                               private val itemClickListener: TwoIntParametersClickListener): RecyclerView.Adapter<CRSelecionaEnvioAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var lastRBChecked: RadioButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_cr_envio, parent, false)
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
        holder.itemView.rb_opcao.text = "Envio ${item.codigo}"

        if(item.pedidoEstoqueRecebimentoID == null) {
            holder.itemView.rb_opcao.setOnClickListener {
                lastRBChecked?.isChecked = false

                holder.itemView.rb_opcao.isChecked = true
                lastRBChecked = holder.itemView.rb_opcao
                itemClickListener.onClick(item.pedidoEstoqueEnvioID, position)
            }
        }
        else{
            holder.itemView.ll_card_view.setBackgroundResource(R.color.preto030)
            holder.itemView.rb_opcao.isClickable = false
        }
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}