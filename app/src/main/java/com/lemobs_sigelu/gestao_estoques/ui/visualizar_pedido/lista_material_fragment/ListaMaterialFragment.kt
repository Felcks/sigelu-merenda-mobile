package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_material_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.material_de_pedido_1
import kotlinx.android.synthetic.main.fragment_pedido_materiais.*

class ListaMaterialFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_pedido_materiais, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.iniciarAdapter()
    }

    private fun iniciarAdapter(){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaMaterialAdapter(context!!, listOf(material_de_pedido_1))
        rv_lista.adapter = adapter
    }
}