package com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_2_cadastra_item

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.sigelu.logistica.App
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.TwoIntParametersClickListener
import com.sigelu.logistica.exceptions.CampoNaoPreenchidoException
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
import com.sigelu.logistica.exceptions.ValorMaiorQuePermitidoException
import com.sigelu.logistica.exceptions.ValorMenorQueZeroException
import com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.CadastraRecebimentoSemEnvioViewModelFactory
import com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_3_confirma.CadastraRecebimentoSEConfirmaActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_material_recebimento_se.*
import javax.inject.Inject

class CadastraRecebimentoSECadastraItemActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemEnvioViewModelFactory
    var viewModel: CadastraRecebimentoSECadastraItemViewModel? = null

    private var adapter: ListaItemRecebimentoSECadastroAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_material_recebimento_se)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraRecebimentoSECadastraItemViewModel::class.java)

        val mainBinding: com.sigelu.logistica.databinding.ActivityCadastraMaterialRecebimentoSeBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_material_recebimento_se)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        val listaItemEnvio = viewModel!!.getItensSolicitados()
        if(listaItemEnvio.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista_material.layoutManager = layoutManager

            this.adapter = ListaItemRecebimentoSECadastroAdapter(App.instance, listaItemEnvio, removerItemListener)
            rv_lista_material.adapter = adapter
        }
        else{
            tv_error.visibility = View.VISIBLE
        }

        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
        }

        ll_layout_anterior.setOnClickListener {
            this.clicouAnterior()
        }

        btn_add.setOnClickListener {
            this.clicouAnterior()
        }

        tv_total_material.text = "(${viewModel!!.getItensSolicitados().size})"
    }

    private fun clicouProximo(){

        try {
            viewModel!!.confirmaCadastroMaterial(this.adapter?.getListaValoresItemEnvio() ?: listOf())

            val intent = Intent(this, CadastraRecebimentoSEConfirmaActivity::class.java)
            startActivity(intent)
        }
        catch (e: NenhumItemSelecionadoException){
            Snackbar.make(ll_all, "Selecione pelo menos um item.", Snackbar.LENGTH_SHORT).show()
        }
        catch (e: CampoNaoPreenchidoException){
            Snackbar.make(ll_all, "Preencha a quantidade.", Snackbar.LENGTH_SHORT).show()
        }
        catch(e: ValorMenorQueZeroException){
            Snackbar.make(ll_all, "Preencha a quantidade com um valor maior que zero.", Snackbar.LENGTH_SHORT).show()
        }
        catch (e: ValorMaiorQuePermitidoException){
            Snackbar.make(ll_all, "Preencha a quantidade com um valor menor que a quantidade disponível.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun clicouAnterior(){
        this.onBackPressed()
    }

    private val removerItemListener = object: TwoIntParametersClickListener {
        override fun onClick(id: Int, position: Int) {
            try{
                viewModel?.removeItem(id)
                adapter?.removeItem(position)
                tv_total_material.text = "(${viewModel!!.getItensSolicitados().size})"
                if(viewModel!!.getItensSolicitados().isEmpty())
                   tv_error.visibility = View.VISIBLE
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
                DialogUtil.buildAlertDialogSimNao(
                    this,
                    "Cancelar recebimento",
                    "Deseja sair e cancelar o recebimento?",
                    {
                        finish()
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}