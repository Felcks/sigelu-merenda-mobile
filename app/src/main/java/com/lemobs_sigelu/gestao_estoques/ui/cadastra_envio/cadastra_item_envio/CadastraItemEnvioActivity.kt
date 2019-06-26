package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraItemEnvioBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_item_envio.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraItemEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraItemEnvioViewModelFactory
    var viewModel: CadastraItemEnvioViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_item_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraItemEnvioViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val mainBinding: ActivityCadastraItemEnvioBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_item_envio)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        val itemEnvio = viewModel!!.getItemSolicitado()
        if(itemEnvio != null) {
            tv_1.text = itemEnvio.itemEstoque?.nomeAlternativo
            tv_2.text = itemEnvio.itemEstoque?.descricao
            tv_3.text = itemEnvio.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
            tv_4.setText(itemEnvio.quantidadeUnidade.toString())
        }
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data.first() is ItemEnvio) {
                        this.iniciarPreenchimento((response.data as ItemEnvio?))
                    }
                }
            }
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel!!.loading.set(true)
    }


    private fun renderErrorState(throwable: Throwable?) {
        viewModel!!.loading.set(false)
    }

    private fun iniciarPreenchimento(itemEnvio: ItemEnvio?){

        if(itemEnvio != null){
            tv_1.text = itemEnvio.itemEstoque?.nomeAlternativo
            tv_2.text = itemEnvio.itemEstoque?.descricao
            tv_3.text = itemEnvio.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
            tv_4.setText(itemEnvio.quantidadeUnidade.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){

            val resultado = viewModel!!.confirmaCadastroMaterial()
            when (resultado) {
                -1.0 -> Toast.makeText(applicationContext, "Digite um valor menor que a quantidade disponível.", Toast.LENGTH_SHORT).show()
                -2.0 -> Toast.makeText(applicationContext, "Digite um valor maior que zero.", Toast.LENGTH_SHORT).show()
                else -> {
                    this.finish()
                    Toast.makeText(applicationContext, "Cadastrou com sucesso", Toast.LENGTH_SHORT).show()
                    //val intent = Intent(this, ConfirmaCadastroPedidoActivity::class.java)
                    //startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}