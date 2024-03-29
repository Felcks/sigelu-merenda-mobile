package com.sigelu.merenda.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sigelu.merenda.R
import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.common.domain.model.accounts.CarregaDados
import com.sigelu.merenda.common.domain.model.accounts.DataHolder
import com.sigelu.merenda.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.merenda.utils.ControladorFonte
import com.sigelu.core.lib.DialogUtil
import com.sigelu.merenda.App
import com.sigelu.merenda.common.domain.repository.PermissaoSistemaRepository
import com.sigelu.merenda.extensions_constants.closeApplication
import com.sigelu.merenda.utils.AppSharedPreferences
import kotlinx.coroutines.runBlocking

class MainActivity: AppCompatActivity() {

    private val permissaoSistemaRepository = PermissaoSistemaRepository()

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

            runBlocking {
                try {
                    val permissoes = permissaoSistemaRepository.carregaPermissao()
                    AppSharedPreferences.setUserPermissoes(App.instance, permissoes.map { it.nome }.toSet())

                }
                catch (t: Throwable){ }
            }

            //Retirar daqui após integração concluída
            AppSharedPreferences.setNucleoID(App.instance, 2)
            AppSharedPreferences.setNucleoNome(App.instance, "Centro")
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