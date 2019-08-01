package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_3_cadastra_item

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_4_confirma.ConfirmaRecebimentoSPActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_item_recebimento_sp.*
import javax.inject.Inject

class CadastraItemActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemPedidoViewModelFactory
    var viewModel: CadastraItemViewModel? = null
    private var adapter:  CadastraItemSemPedidoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_item_recebimento_sp)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraItemViewModel::class.java)
        val mainBinding: com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraItemRecebimentoSpBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_item_recebimento_sp)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        val listaItemEnvio = viewModel!!.getItensEstoque()
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista_material.layoutManager = layoutManager
        this.adapter = CadastraItemSemPedidoAdapter(App.instance, listaItemEnvio)
        rv_lista_material.adapter = adapter


        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
        }

        ll_layout_anterior.setOnClickListener {
            viewModel!!.removeItens()
            onBackPressed()
        }

        tv_total_material.text = "(${viewModel!!.getItensEstoque().size})"

    }

    fun clicouProximo(){
        try{

            try {
                viewModel!!.confirmaCadastroMaterial(this.adapter?.getListaValoresItemEnvio() ?: listOf())

                val intent = Intent(this, ConfirmaRecebimentoSPActivity::class.java)
                startActivity(intent)
            }
            catch (e: NenhumItemSelecionadoException){
                Snackbar.make(ll_all, "Selecione pelo menos um item.", Snackbar.LENGTH_SHORT).show()
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
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
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
                    "Deseja cancelar o cadastro de recebimento?",
                    {
                        viewModel!!.removeItens()
                        finish()
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)

                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}