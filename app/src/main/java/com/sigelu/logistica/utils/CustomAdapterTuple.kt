package com.sigelu.logistica.utils

import android.widget.TextView
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.sigelu.logistica.R
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