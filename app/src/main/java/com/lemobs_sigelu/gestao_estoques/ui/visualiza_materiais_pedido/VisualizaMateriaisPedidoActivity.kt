package com.lemobs_sigelu.gestao_estoques.ui.visualiza_materiais_pedido

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import kotlinx.android.synthetic.main.activity_visualiza_materiais_cadastrados.*

class VisualizaMateriaisPedidoActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_cadastrados)
        this.iniciarToolbar()
    }

    private fun iniciarToolbar(){
        if(toolbar != null){

            toolbar.setNavigationIcon(R.drawable.ic_cancel)
            toolbar.setNavigationOnClickListener {
                //this.onBackPressed()
            }
            setSupportActionBar(toolbar)
        }
    }
}