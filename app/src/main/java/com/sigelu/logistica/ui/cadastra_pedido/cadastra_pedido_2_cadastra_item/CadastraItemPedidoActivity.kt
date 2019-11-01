package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sigelu.logistica.App
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ActivityDeFluxo
import com.sigelu.logistica.common.domain.model.TwoIntParametersClickListener
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.common.viewmodel.Status
import com.sigelu.logistica.databinding.ActivityCpCadastraItemPedidoBinding
import com.sigelu.logistica.exceptions.CampoNaoPreenchidoException
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
import com.sigelu.logistica.exceptions.ValorMenorQueZeroException
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_confirma.ConfirmaCadastroPedidoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cp_cadastra_item_pedido.*
import kotlinx.android.synthetic.main.activity_cp_cadastra_item_pedido.ll_all
import org.koin.android.ext.android.inject

class CadastraItemPedidoActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: CadastraItemPedidoViewModel by inject()
    private var adapter: ListaItemEstoqueAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_cadastra_item_pedido)

        val binding: ActivityCpCadastraItemPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cp_cadastra_item_pedido)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val listaItemEnvio = viewModel.getItensCadastrados()
        if(listaItemEnvio.isNotEmpty()) {

            iniciaListaAdapter(listaItemEnvio)
            tv_total_material.text = String.format(resources.getString(R.string.numero_em_parenteses), viewModel.getItensCadastrados().size)
        }
        else{
            tv_error.visibility = View.VISIBLE
        }

        btn_add.setOnClickListener { clicouAnterior() }

        this.iniciaStepper()
    }

    private fun iniciaStepper(){
        top_stepper.setFluxo(viewModel.getFluxo())
        bottom_stepper.setFluxo(viewModel.getFluxo())

        top_stepper.atualiza()
        bottom_stepper.atualiza()

        bottom_stepper.setAnteriorOnClickListener { clicouAnterior() }
        bottom_stepper.setProximoOnClickListener { clicouProximo() }
    }

    private fun iniciaListaAdapter(lista: List<MaterialDTO>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista_material.layoutManager = layoutManager
        rv_lista_material.setItemViewCacheSize(lista.size)
        rv_lista_material.setHasFixedSize(true)

        this.adapter = ListaItemEstoqueAdapter(App.instance, lista as MutableList<MaterialDTO>, removerItemListener)
        rv_lista_material.adapter = adapter
    }


    private val removerItemListener = object: TwoIntParametersClickListener {
        override fun onClick(id: Int, pos: Int) {
            try{
                viewModel.removeItem(id)
                adapter?.removeItem(pos)
                tv_total_material.text = String.format(resources.getString(R.string.numero_em_parenteses), viewModel.getItensCadastrados().size)

                if(viewModel.getItensCadastrados().isEmpty())
                    tv_error.visibility = View.VISIBLE
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun clicouProximo(){

        if(viewModel.carregandoProximaTela.value?.status != Status.EMPTY_RESPONSE)
            return

        try {
            viewModel.carregandoProximaTela.value = Response.loading()
            viewModel.confirmaCadastroMaterial(this.adapter?.getListaMateriaisPreenchidos() ?: listOf())

            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, ConfirmaCadastroPedidoActivity::class.java)
            startActivity(intent)
        }
        catch (e: NenhumItemSelecionadoException){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, "Cadastre pelo menos um item.", Snackbar.LENGTH_SHORT).show()
        }
        catch (e: CampoNaoPreenchidoException){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, "Preencha a quantidade.", Snackbar.LENGTH_SHORT).show()
        }
        catch(e: ValorMenorQueZeroException){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, "Preencha a quantidade com um valor maior que zero.", Snackbar.LENGTH_SHORT).show()
        }
        catch(erro: java.lang.Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, "Ocorreu algum erro inesperado.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior(){
        this.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregandoProximaTela.value = Response.empty()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onBackPressed() {
        viewModel.getFluxo().decrementaPassoAtual()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
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

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        return true
    }
}