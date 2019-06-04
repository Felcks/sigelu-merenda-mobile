package com.lemobs_sigelu.gestao_estoques.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.startApp()
    }

    private fun startApp(){

        if(AppSharedPreferences.getUserId(App.instance) != -1){

            RestApi.auth = AppSharedPreferences.getUserToken(this)

            val intent = Intent(this, ListaPedidoActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}