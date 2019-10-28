package com.lemobs_sigelu.gestao_estoques.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.CarregaDados
import com.lemobs_sigelu.gestao_estoques.common.domain.model.DataHolder
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.ControladorFonte
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.startApp()
        this.checkFontSize()

        if (!DataHolder.carregado()) {
            if (intent != null) CarregaDados(intent.getBundleExtra("sigelu.dados"))
        }

        if (DataHolder.IsAmbienteCorreto() == false) {
            DialogUtil.buildAlertDialogOk(this@MainActivity,
                "Alerta de Inconsistência",
                "Os ambientes do Launcher e deste aplicativo diferem. O aplicativo será fechado.",
                {
                    CarregaDados.limpar()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DataHolder.getSchemeLauncher()))
                    intent.action = Intent.ACTION_VIEW
                    startActivity(intent)
                    finish()
                },
                cancelavel = false)
        }
    }

    private fun checkFontSize() {
        val controladorFonte = ControladorFonte(this)
        controladorFonte.atualizarTamanhoFonte()
    }

    private fun startApp(){

        RestApi.auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImVuY2FycmVnYWRvY2VudHJvMSIsImlhdCI6MTU0MDE3MzgxN30.R7noAbPGOX-96VPKqkN-h30okKjcd21g6kPGAhImEyc"
        //DataHolder.token

        val intent = Intent(this, ListaPedidoActivity::class.java)
        startActivity(intent)
        finish()
    }

}