package com.lemobs_sigelu.gestao_estoques.utils

import android.annotation.SuppressLint
import android.widget.TextView
import android.view.ViewGroup
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.lemobs_sigelu.gestao_estoques.R
import android.graphics.Color.parseColor
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.graphics.Color
import android.widget.BaseAdapter



/**
 * Created by felcks on Jun, 2019
 */
class CustomAdapterTuple(val context: Context, val list: List<RowItem>) : BaseAdapter() {

    var inflter: LayoutInflater = (LayoutInflater.from(context))

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): RowItem? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowItem = getItem(position)

        val rowView = inflter.inflate(R.layout.item_tuple_spinner, null)

        val tvTipo = rowView.findViewById(R.id.tv_tipo) as? TextView
        tvTipo?.text = rowItem?.tipo

        val tvNome = rowView.findViewById(R.id.tv_nome) as? TextView
        tvNome?.text = rowItem?.nome

        return rowView
    }

    data class RowItem(val tipo: String, val nome: String)
}