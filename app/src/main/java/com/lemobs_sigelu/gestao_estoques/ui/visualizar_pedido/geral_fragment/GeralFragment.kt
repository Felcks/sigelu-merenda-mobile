package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.geral_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.VisualizarPedidoViewModel

class GeralFragment(): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_pedido_geral, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}