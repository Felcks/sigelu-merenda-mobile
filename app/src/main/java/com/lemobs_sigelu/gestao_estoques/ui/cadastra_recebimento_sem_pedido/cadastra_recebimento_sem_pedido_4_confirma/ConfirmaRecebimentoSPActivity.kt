package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_4_confirma

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecebimentoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoViewModelFactory
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_materiais_cadastrados.*
import javax.inject.Inject

class ConfirmaRecebimentoSPActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemPedidoViewModelFactory
    var viewModel: ConfirmaRecebimentoSPViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_cadastrados)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaRecebimentoSPViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

//        btn_adicionar_materiais.setOnClickListener {
//            val intent = Intent(this, SelecionaItemEnvioRecebimentoActivity::class.java)
//            startActivity(intent)
//        }
        this.iniciarToolbar()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            //this.iniciarAdapter(result as List<ItemRecebimento>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {}

    private fun iniciarToolbar(){
        if(toolbar != null){

            toolbar.setNavigationIcon(R.drawable.ic_cancel)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                mostrarDialogCancelamento()
            }
        }
    }

    private fun mostrarDialogCancelamento(){

        DialogUtil.buildAlertDialogSimNao(this,
            "Cancelar recebimento",
            "Deseja cancelar o cadastro de recebimento?",
            {
//                this.viewModel!!.cancelaRecebimento()
//                val intent = Intent(this, ListaPedidoActivity::class.java)
//                startActivity(intent)
//                this.finishAffinity()
            },
            {}).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuInflater.inflate(R.menu.menu_done, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){
            //viewModel!!.enviaRecebimento()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.mostrarDialogCancelamento()
    }
}