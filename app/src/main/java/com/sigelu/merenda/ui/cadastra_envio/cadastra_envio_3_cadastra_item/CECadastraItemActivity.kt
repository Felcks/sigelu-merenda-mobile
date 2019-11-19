package com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_3_cadastra_item

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
import com.sigelu.merenda.App
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.ActivityDeFluxo
import com.sigelu.merenda.common.domain.model.ItemEstoque
import com.sigelu.merenda.common.domain.model.TwoIntParametersClickListener
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.databinding.ActivityCeCadastraItemBinding
import com.sigelu.merenda.exceptions.CampoNaoPreenchidoException
import com.sigelu.merenda.exceptions.ItemSemQuantidadeDisponivelException
import com.sigelu.merenda.exceptions.NenhumItemSelecionadoException
import com.sigelu.merenda.exceptions.ValorMenorQueZeroException
import com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_4_confirma.CEConfirmaActivity
import com.sigelu.merenda.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_ce_cadastra_item.*
import org.koin.android.ext.android.inject

class CECadastraItemActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: CECadastraItemViewModel by inject()
    private var adapter: ListaItemEstoqueAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ce_cadastra_item)

        val binding: ActivityCeCadastraItemBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_ce_cadastra_item
        )
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val listaItemEnvio = viewModel.getItensCadastrados()
        if(listaItemEnvio.isNotEmpty()) {

            iniciaListaAdapter(listaItemEnvio)
            tv_total_material.text = "(${listaItemEnvio.size})"
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

    private fun iniciaListaAdapter(lista: List<ItemEstoque>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista_material.layoutManager = layoutManager
        rv_lista_material.setItemViewCacheSize(lista.size)
        rv_lista_material.setHasFixedSize(true)

        this.adapter = ListaItemEstoqueAdapter(App.instance, lista, removerItemListener)
        rv_lista_material.adapter = adapter
    }


    private val removerItemListener = object: TwoIntParametersClickListener {
        override fun onClick(id: Int, position: Int) {
            try{
                viewModel.removeItem(id)
                adapter?.removeItem(position)
                tv_total_material.text = "(${viewModel.getItensCadastrados().size})"

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
            viewModel.confirmaCadastroMaterial(this.adapter?.getLista() ?: listOf())

            viewModel.getFluxo().incrementaPassoAtual()
            val intent = Intent(this, CEConfirmaActivity::class.java)
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
            Snackbar.make(ll_all, "Preencha a quantidade com um valor maior que zero.", Snackbar .LENGTH_SHORT).show()
        }
        catch (e: ItemSemQuantidadeDisponivelException){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, "Preencha a quantidade com valor disponível.", Snackbar.LENGTH_SHORT).show()
        }
        catch (e: java.lang.Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior(){
        this.onBackPressed()
        viewModel.getFluxo().decrementaPassoAtual()
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregandoProximaTela.value = Response.empty()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
            DialogUtil.buildAlertDialogSimNao(
                this,
                "Cancelar envio ",
                "Deseja sair e cancelar o envio?",
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