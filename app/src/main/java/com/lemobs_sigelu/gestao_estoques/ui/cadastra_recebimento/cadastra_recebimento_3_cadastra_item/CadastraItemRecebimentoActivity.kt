package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item

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
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.esconderTeclado
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecebimentoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_4_confirma.ConfirmaRecebimentoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_material_recebimento.*
import javax.inject.Inject

class CadastraItemRecebimentoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoViewModelFactory
    var viewModel: CadastraItemRecebimentoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraItemRecebimentoViewModel::class.java)
        val mainBinding: com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraMaterialRecebimentoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_material_recebimento)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){

            try {
                tv_5.esconderTeclado()
                viewModel!!.confirmaCadastroItem()
                val intent = Intent(this, ConfirmaRecebimentoActivity::class.java)
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

//            val resultado = viewModel!!.confirmaCadastroMaterial()
//            when (resultado) {
//                -1.0 -> Toast.makeText(applicationContext, "Digite um valor menor que a quantidade disponível.", Toast.LENGTH_SHORT).show()
//                -2.0 -> Toast.makeText(applicationContext, "Digite um valor maior que zero.", Toast.LENGTH_SHORT).show()
//                else -> {
//                    viewModel!!.cadastraQuantidadeMaterial(resultado)
//                    val intent = Intent(this, ConfirmaRecebimentoActivity::class.java)
//                    startActivity(intent)
//                    this.finish()
//                }
//            }
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