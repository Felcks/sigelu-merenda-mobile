package com.lemobs_sigelu.gestao_estoques.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.accounts.CarregaDados
import com.lemobs_sigelu.gestao_estoques.common.domain.model.accounts.DataHolder
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.ControladorFonte
import com.sigelu.core.lib.DialogUtil

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!DataHolder.carregado()) {
            if (intent != null) CarregaDados(
                intent.getBundleExtra("sigelu.dados")
            )
        }

        if(!DataHolder.carregado()) {
            DialogUtil.buildAlertDialogOk(
                this@MainActivity,
                "Aviso",
                "O aplicativo não pode ser aberto diretamente. Abra pelo Sigelu.",
                {
                    closeApplication()
                },
                cancelavel = false
            ).show()
        }
        else if (DataHolder.IsAmbienteCorreto() == false) {
            DialogUtil.buildAlertDialogOk(this@MainActivity,
                "Alerta de Inconsistência",
                "Os ambientes do Launcher e deste aplicativo diferem. O aplicativo será fechado.",
                {
                    backToLauncher()
                },
                cancelavel = false
            ).show()
        }
        else{
            this.startApp()
            this.checkFontSize()
        }
    }

    private fun backToLauncher(){
        CarregaDados.limpar()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DataHolder.getSchemeLauncher()))
        intent.action = Intent.ACTION_VIEW
        startActivity(intent)
        finish()
    }

    private fun closeApplication(){
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())
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