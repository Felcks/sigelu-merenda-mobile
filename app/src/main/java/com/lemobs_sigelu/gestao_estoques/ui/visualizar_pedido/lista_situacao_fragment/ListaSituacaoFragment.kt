package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_situacao_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.situacoes_de_pedido
import kotlinx.android.synthetic.main.fragment_pedido_situacoes.*

class ListaSituacaoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_pedido_situacoes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.iniciarAdapter()
    }

    private fun iniciarAdapter(){

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaSituacaoAdapter(context!!, situacoes_de_pedido)
        rv_lista.adapter = adapter
    }
}