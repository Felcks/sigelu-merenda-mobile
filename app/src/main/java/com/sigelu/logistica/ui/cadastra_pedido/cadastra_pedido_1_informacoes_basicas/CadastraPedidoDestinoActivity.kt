package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.*
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.common.viewmodel.Status
import com.sigelu.logistica.databinding.ActivityCadastraPedidoDestinoBinding
import com.sigelu.logistica.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoActivity
import com.sigelu.logistica.utils.CustomAdapterTuple
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_pedido_destino.*
import java.lang.Exception
import javax.inject.Inject

class CadastraPedidoDestinoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraPedidoDestinoViewModel? = null

    private var colorAccent: Int = 0
    private var colorAccentDark: Int = 0
    private var adapter: ListaObraAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_destino)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraPedidoDestinoViewModel::class.java)
        viewModel!!.responseNucleos.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseEmpresas.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseObras.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseContratos.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.origem().observe(this, Observer<Local> { response -> processResponseOrigem(response) })

        viewModel!!.carregaListaNucleo()
        viewModel!!.carregaListaContrato()

        this.colorAccent = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        this.colorAccentDark = ContextCompat.getColor(applicationContext, R.color.colorAccentDark)

        val mainBinding: ActivityCadastraPedidoDestinoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_pedido_destino)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()
    }

    /* Process Response Nucleo */
    private var contadorCarregamentos = 0
    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoading()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is Nucleo)
                        renderDataNucleo(response.data)

                    if(response.data[0] is Fornecedor)
                        renderDataFornecedor(response.data)

                    if(response.data[0] is Obra)
                        renderDataObra(response.data)

                    if(response.data[0] is ContratoEstoque)
                        renderDataContrato(response.data)

                    contadorCarregamentos++
                    if(contadorCarregamentos >= 4){
                        viewModel!!.loading.set(false)
                    }
                }

            }
            Status.ERROR -> {
                renderError(response.error)
            }
        }
    }

    private fun renderLoading() {
        viewModel!!.loading.set(true)
    }

    private fun renderError(throwable: Throwable?) {
        viewModel!!.loading.set(false)
    }

    private fun renderDataNucleo(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaOrigem.addAll((result as List<Nucleo>).map { Local(it.id, "Núcleo", it.nome) })
            viewModel!!.listaDestino.addAll((result).map { Local(it.id,"Núcleo",it.nome) })
            this.iniciarSpinnerDestinoParaFornecedor(result)
        }

        viewModel!!.carregaListaFornecedor()
        viewModel!!.carregaListaObra()
    }

    private fun renderDataFornecedor(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaOrigem.addAll((result as List<Fornecedor>).map { Local(it.id,"Fornecedor", it.nome ?: "") })
        }
        iniciarSpinnerOrigem()
    }

    private fun renderDataObra(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaDestino.addAll((result as List<Obra>).map { Local(it.id,"Obra",it.codigo) })
        }
        iniciarSpinnerDestinoParaNucleo()
    }

    private fun renderDataContrato(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaContrato.addAll((result as List<ContratoEstoque>))
        }
        iniciarSpinnerContratos()
    }

    fun processResponseOrigem(response: Local?) {

        if(response?.tipo == "Núcleo"){
            if(viewModel!!.origemIsNucleo.get() == false){
                viewModel!!.resetDestino()
                spinner_destino_para_nucleo.setSelection(0)
            }
            viewModel!!.origemIsNucleo.set(true)
        }
        else{
            if(viewModel!!.origemIsNucleo.get() == true){
                viewModel!!.resetDestino()
                spinner_destino_para_fornecedor.setSelection(0)
            }
            viewModel!!.origemIsNucleo.set(false)
        }
    }

    fun iniciarSpinnerOrigem(){

        val listaTextoOrigem = viewModel!!.listaOrigem.map { CustomAdapterTuple.RowItem(it.tipo, it.nome) }
        var adapter = CustomAdapterTuple(this, listaTextoOrigem)
        spinner_origem.adapter = adapter

        spinner_origem.onItemSelectedListener = viewModel!!.selecionadorOrigem
    }

    fun iniciarSpinnerDestinoParaNucleo(){

        val textoDestino = viewModel!!.listaDestino.map { CustomAdapterTuple.RowItem(it.tipo, it.nome) }
        var adapter = CustomAdapterTuple(this, textoDestino)
        spinner_destino_para_nucleo.adapter = adapter

        spinner_destino_para_nucleo.onItemSelectedListener = viewModel!!.selecionadorDestino
    }

    fun iniciarSpinnerDestinoParaFornecedor(nucleos: List<Nucleo>){

        val textoDestino = nucleos.map { CustomAdapterTuple.RowItem("Núcleo", it.nome) }
        var adapter = CustomAdapterTuple(this, textoDestino)
        spinner_destino_para_fornecedor.adapter = adapter

        spinner_destino_para_fornecedor.onItemSelectedListener = viewModel!!.selecionadorDestino
    }

    fun iniciarSpinnerContratos(){

        val textoDestino = viewModel!!.listaContrato.map { CustomAdapterTuple.RowItem(it.numeroContrato, it.situacao) }
        var adapter = CustomAdapterTuple(this, textoDestino)
        spinner_contrato.adapter = adapter

        spinner_contrato.onItemSelectedListener = viewModel!!.selecionadorContrato
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ==  R.id.btn_done){

            try{
                viewModel!!.confirmaPedido()
                val intent = Intent(this, SelecionaItemPedidoActivity::class.java)
                startActivity(intent)
            }
            catch (e: Exception){
                Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
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