package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_1_cadastra_item_contrato

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
import com.sigelu.logistica.databinding.ActivityCadastraMaterialPedidoBinding
import com.sigelu.logistica.exceptions.CampoNaoPreenchidoException
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
import com.sigelu.logistica.exceptions.ValorMaiorQuePermitidoException
import com.sigelu.logistica.exceptions.ValorMenorQueZeroException
import com.sigelu.logistica.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_1_confirma.ConfirmaCadastroPedidoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_material_pedido.*
import java.lang.Exception
import javax.inject.Inject

class CadastraItemPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraItemPedidoViewModel? = null

    private var adapter: ListaItemContratoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraItemPedidoViewModel::class.java)

        val mainBinding: ActivityCadastraMaterialPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_material_pedido)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        val listaItemEnvio = viewModel!!.getItensSolicitados()
        if(listaItemEnvio.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista_material.layoutManager = layoutManager

            this.adapter = ListaItemContratoAdapter(App.instance, listaItemEnvio, removerItemListener)
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
            if(this.adapter == null)
                throw Exception("Nenhum item cadastrado.")

            viewModel!!.confirmaCadastroMaterial(this.adapter?.getListaValoresItemEnvio() ?: listOf())

            val intent = Intent(this, ConfirmaCadastroPedidoActivity::class.java)
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
        catch (e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
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
                    "Cancelar RM ",
                    "Deseja sair e cancelar a RM?",
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