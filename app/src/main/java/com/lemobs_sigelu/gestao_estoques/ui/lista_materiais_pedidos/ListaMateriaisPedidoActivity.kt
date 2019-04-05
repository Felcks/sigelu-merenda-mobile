package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.pedido_1

class ListaMateriaisPedidoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_materiais_pedido)

        val pedido = pedido_1
    }
}