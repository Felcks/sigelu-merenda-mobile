package com.lemobs_sigelu.gestao_estoques

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_troca_tela.setOnClickListener {
            val intent = Intent(this, ListaMaterialActivity::class.java)
            startActivity(intent)
        }
    }
}