package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraItemEnvioBinding
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.esconderTeclado
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.CadastraEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma.ConfirmaCadastroEnvioActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_item_envio.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraItemEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraEnvioViewModelFactory
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

            try {
                tv_5.esconderTeclado()
                viewModel!!.confirmaCadastroMaterial()

                val intent = Intent(this, ConfirmaCadastroEnvioActivity::class.java)
                startActivity(intent)
            }
            catch (e: CampoNaoPreenchidoException){
                Snackbar.make(ll_all, "Preencha a quantidade.", Snackbar.LENGTH_SHORT).show()
            }
            catch(e: ValorMenorQueZeroException){
                Snackbar.make(ll_all, "Preencha a quantidade com um valor maior que zero.", Snackbar.LENGTH_LONG).show()
            }
            catch (e: ValorMaiorQuePermitidoException){
                Snackbar.make(ll_all, "Preencha a quantidade com um valor menor que a quantidade disponível.", Snackbar.LENGTH_LONG).show()
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

    override fun onBackPressed() {
        viewModel!!.removeUltimoItemSelecionado()
        super.onBackPressed()
    }
}