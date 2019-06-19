package com.lemobs_sigelu.gestao_estoques.ui.recebimento.seleciona_itemenvio_recebimento

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.recebimento.cadastra_item_recebimento.CadastraItemRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido.*
import javax.inject.Inject

class SelecionaItemEnvioRecebimentoActivity: AppCompatActivity(), ISelecionaMaterial {

    @Inject
    lateinit var viewModelFactory: SelecionaItemEnvioRecebimentoViewModelFactory
    var viewModel: SelecionaItemEnvioRecebimentoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemEnvioRecebimentoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarListaMateriais()
    }

    override fun selecionaMaterial(materialId: Int?) {

        if(materialId != null) {

            val successSelecionaMaterial = viewModel!!.selecionaMaterial(materialId)
            if (successSelecionaMaterial) {
                FlowSharedPreferences.setItemEnvioID(App.instance, materialId)
                val intent = Intent(this, CadastraItemRecebimentoActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarAdapter(list: List<ItemEnvio>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter =
            ListaItemEnvioSelecionavelSimplesAdapter(
                applicationContext,
                list,
                this
            )
        rv_lista.adapter = adapter
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderErrorState(throwable: Throwable?) {
        tv_error.visibility = View.VISIBLE
    }

    private fun renderDataState(result: Any?) {

        tv_error.visibility = View.GONE
        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemEnvio>)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}