package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.j256.ormlite.support.ConnectionSource
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.bd.*
import com.lemobs_sigelu.gestao_estoques.bd_model.*
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaPedidoAdapter
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_pedido.*
import java.util.*
import javax.inject.Inject

class ListaPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaPedidoViewModelFactory
    var viewModel: ListaPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedido)

        if(AppSharedPreferences.getPrimeiraVez(applicationContext)){
            this.mockPedidos()
            AppSharedPreferences.setPrimeiraVez(applicationContext)
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaPedido()

        this.iniciarAdapter(listOf())

        menu_item_cadastrar_pedido.setOnClickListener {
            val intent = Intent(this, CadastraPedidoDestinoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mockPedidos(){

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        val situacaoDAO = SituacaoDAO(DatabaseHelper.connectionSource)

        val situacaoDTO1 = SituacaoDTO(1, "Em análise")
        val situacaoDTO2 = SituacaoDTO(2, "Aprovado")
        val situacaoDTO3 = SituacaoDTO(3, "Entregue")
        val situacaoDTO4 = SituacaoDTO(4, "Reprovado")
        val situacaoDTO5 = SituacaoDTO(5, "Parcial")

        val pedido_1 = PedidoDTO(1, "180001", "Inoã", "Centro", Date(), Date(), situacaoDTO1)
        val pedido_2 = PedidoDTO(2, "180002", "Calaboca", "Capuaçu", Date(), Date(), situacaoDTO2)
        val pedido_3 = PedidoDTO(3, "180003", "Itacuruça", "Itaguai", Date(), Date(), situacaoDTO3)
        val pedido_4 = PedidoDTO(4, "180004", "Venezuela", "Matro grosso", Date(), Date(), situacaoDTO4)
        val pedido_5 = PedidoDTO(5, "180005", "Israel", "florentina", Date(), Date(), situacaoDTO5)
        val mutableList = mutableListOf<PedidoDTO>(pedido_1, pedido_2, pedido_3, pedido_4, pedido_5)

        for(pedido in mutableList) {
            situacaoDAO.add(pedido.situacao!!)
            pedidoDAO.add(pedido)
        }

        /* PEDIDO 1 */
        val situacaoHistorico = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_1)
        val situacaoHistoricoDAO = SituacaoHistoricoDAO(DatabaseHelper.connectionSource)
        situacaoHistoricoDAO.add(situacaoHistorico)
        pedido_1.historico_situacoes = arrayListOf(situacaoHistorico)
        pedidoDAO.add(pedido_1)

        val unidadeMedida1 = UnidadeMedidaDTO(1, "Quilograma", "kg")
        val unidadeMedida2 = UnidadeMedidaDTO(2, "Litro", "lt")
        val unidadeMedidaDAO = UnidadeMedidaDAO(DatabaseHelper.connectionSource)
        unidadeMedidaDAO.add(unidadeMedida1)
        unidadeMedidaDAO.add(unidadeMedida2)

        val materialBase = MaterialDTO(1, "Areia", "A areia fica quente no verão", unidadeMedida1)
        val materialDAO = MaterialDAO(DatabaseHelper.connectionSource)
        materialDAO.add(materialBase)

        val materialPedido = MaterialDePedidoDTO(null, materialBase,
            1000.0, 0.0, pedido_1)
        val materialPedidoDAO = MaterialDePedidoDAO(DatabaseHelper.connectionSource)
        materialPedidoDAO.add(materialPedido)
        pedido_1.materiais = arrayListOf(materialPedido)
        pedidoDAO.add(pedido_1)


        /* PEDIDO 2 */
        val situacaoHistorico2 = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_2)
        val situacaoHistorico3 = SituacaoHistoricoDTO(null, "Aprovado", Date(), pedido_2)
        val situacaoHistorico4 = SituacaoHistoricoDTO(null, "Entrega 1", Date(), pedido_2)
        situacaoHistoricoDAO.add(situacaoHistorico2)
        situacaoHistoricoDAO.add(situacaoHistorico3)
        situacaoHistoricoDAO.add(situacaoHistorico4)
        pedido_2.historico_situacoes = arrayListOf(situacaoHistorico, situacaoHistorico2)
        pedidoDAO.add(pedido_2)

        val materialBase3 = MaterialDTO(2, "Água", "A água é molhada", unidadeMedida2)
        materialDAO.add(materialBase3)

        val materialPedido2 = MaterialDePedidoDTO(null, materialBase,
            333.0, 50.0, pedido_2)
        val materialPedido3 = MaterialDePedidoDTO(null, materialBase3,
            250.0, 100.0, pedido_2)


        materialPedidoDAO.add(materialPedido2)
        materialPedidoDAO.add(materialPedido3)
        pedido_2.materiais = arrayListOf(materialPedido2, materialPedido3)
        pedidoDAO.add(pedido_2)


        ///MOCK MATERIAIS
        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        val materialDeCadastro1 = MaterialDeCadastroDTO(1, materialBase, 100.0)
        val materialDeCadastro2 = MaterialDeCadastroDTO(2, materialBase3, 50.0)
        materialDeCadastroDAO.add(materialDeCadastro1)
        materialDeCadastroDAO.add(materialDeCadastro2)
    }

    private fun mockSituacoesDePedido(){


    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
    }

    private fun iniciarAdapter(list: List<Pedido>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaPedidoAdapter(
            applicationContext,
            list,
            this,
            visualizarPedidoClickListener
        )
        rv_lista.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_atualiza, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.btn_update -> {
                viewModel!!.carregaListaPedido()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val entregaClickListener = View.OnClickListener {
        val intent = Intent(applicationContext, EntregaMateriaisPedidoActivity::class.java)
        startActivity(intent)
    }

    fun entregaPedido(pedidoID: Int){

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        val pedidoDTO = pedidoDAO.queryForId(pedidoID)

        if(pedidoDTO != null){
            viewModel!!.armazenaPedidoNoFluxo(applicationContext,pedidoID)
            val intent = Intent(applicationContext, EntregaMateriaisPedidoActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Ocorreu um erro desconhecido", Toast.LENGTH_SHORT).show()
        }
    }

    private val visualizarPedidoClickListener = object : ListClickListener {
        override fun onClick(id: Int) {

            viewModel!!.armazenaPedidoNoFluxo(applicationContext,id)
            val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
            startActivity(intent)
        }
    }
}