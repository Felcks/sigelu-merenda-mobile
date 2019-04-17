package com.lemobs_sigelu.gestao_estoques.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import kotlinx.android.synthetic.main.item_material_adicao.view.*

class ListaMaterialDeCadastroAdapter(val context: Context,
                                     val list: List<*>): RecyclerView.Adapter<ListaMaterialDeCadastroAdapter.MyViewHolder>() {

    val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_material_adicao, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = this.list[position]

        if(item is MaterialParaCadastro){
            holder.itemView.tv_name.text = item.nome

            holder.itemView.btn_add.setOnClickListener {
                
            }
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}