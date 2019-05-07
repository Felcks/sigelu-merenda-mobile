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
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
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
    var adapter: ListaPedidoAdapter? = null

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

        val pedido_1 = PedidoDTO(1, "330001", "-", "Núcleo 1", Date(), Date(), situacaoDTO1)
        val pedido_2 = PedidoDTO(2, "330002", "Núcleo Central", "Núcleo 1", Date(), Date(), situacaoDTO2)
        val pedido_3 = PedidoDTO(3, "330003", "Núcleo 2", "Obra 180001", Date(), Date(), situacaoDTO3)
        val pedido_4 = PedidoDTO(4, "330004", "-", "Obra 180017", Date(), Date(), situacaoDTO4)
        val pedido_5 = PedidoDTO(5, "330005", "Fornecedor", "Núcleo 1", Date(), Date(), situacaoDTO5)
        val mutableList = mutableListOf<PedidoDTO>(pedido_1, pedido_2, pedido_3, pedido_4, pedido_5)
        for(pedido in mutableList) {
            situacaoDAO.add(pedido.situacao!!)
            pedidoDAO.add(pedido)
        }

        /* Unidade de Medida */
        val unidadeMedidaDAO = UnidadeMedidaDAO(DatabaseHelper.connectionSource)
        val un_quilograma = UnidadeMedidaDTO(1, "Quilograma", "kg")
        val un_metro_cubico = UnidadeMedidaDTO(3, "Metro cúbico", "m³")
        val un_unidade = UnidadeMedidaDTO(4, "Unidade", "un.")
        val un_metro = UnidadeMedidaDTO(5, "Metro", "m")
        val un_litro_por_metro = UnidadeMedidaDTO(6, "Litro por Metro", "L/m")

        unidadeMedidaDAO.add(un_quilograma)
        unidadeMedidaDAO.add(un_metro_cubico)
        unidadeMedidaDAO.add(un_unidade)
        unidadeMedidaDAO.add(un_metro)
        unidadeMedidaDAO.add(un_litro_por_metro)

        /* Material Base */
        val materialDAO = MaterialDAO(DatabaseHelper.connectionSource)
        val material_arame_queimado = MaterialDTO(1, "Arame Queimado", "Sem descrição", un_quilograma)
        val material_areia = MaterialDTO(2, "Areia", "Sem descrição", un_metro_cubico)
        val material_boca_de_lobo = MaterialDTO(3, "Boca de Lobo", "Sem descrição", un_unidade)
        val material_tabua = MaterialDTO(4, "Tábua", "Sem descrição", un_metro)
        materialDAO.add(material_arame_queimado)
        materialDAO.add(material_areia)
        materialDAO.add(material_boca_de_lobo)
        materialDAO.add(material_tabua)

        /* PEDIDO 1 */
        val situacaoHistorico = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_1)
        val situacaoHistoricoDAO = SituacaoHistoricoDAO(DatabaseHelper.connectionSource)
        situacaoHistoricoDAO.add(situacaoHistorico)
        pedido_1.historico_situacoes = arrayListOf(situacaoHistorico)
        pedidoDAO.add(pedido_1)

        val materialPedido = MaterialDePedidoDTO(null, material_arame_queimado, 100.0, 0.0, pedido_1)
        val materialPedidoDAO = MaterialDePedidoDAO(DatabaseHelper.connectionSource)
        materialPedidoDAO.add(materialPedido)
        pedido_1.materiais = arrayListOf(materialPedido)
        pedidoDAO.add(pedido_1)


        /* PEDIDO 2 */
        val situacaoHistorico2 = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_2)
        val situacaoHistorico3 = SituacaoHistoricoDTO(null, "Aprovado", Date(), pedido_2)
        situacaoHistoricoDAO.add(situacaoHistorico2)
        situacaoHistoricoDAO.add(situacaoHistorico3)
        pedido_2.historico_situacoes = arrayListOf(situacaoHistorico2, situacaoHistorico3)
        pedidoDAO.add(pedido_2)

        val materialPedido2 = MaterialDePedidoDTO(null, material_areia, 333.0, 0.0, pedido_2)
        val materialPedido3 = MaterialDePedidoDTO(null, material_boca_de_lobo, 250.0, 0.0, pedido_2)
        materialPedidoDAO.add(materialPedido2)
        materialPedidoDAO.add(materialPedido3)
        pedido_2.materiais = arrayListOf(materialPedido2, materialPedido3)
        pedidoDAO.add(pedido_2)

        /* PEDIDO 3 */
        val situacaoHistorico_3_1 = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_3)
        val situacaoHistorico_3_2 = SituacaoHistoricoDTO(null, "Aprovado", Date(), pedido_3)
        val situacaoHistorico_3_3 = SituacaoHistoricoDTO(null, "Entregue", Date(), pedido_3)
        situacaoHistoricoDAO.add(situacaoHistorico_3_1)
        situacaoHistoricoDAO.add(situacaoHistorico_3_2)
        situacaoHistoricoDAO.add(situacaoHistorico_3_3)
        pedido_3.historico_situacoes = arrayListOf(situacaoHistorico_3_1, situacaoHistorico_3_2, situacaoHistorico_3_3)
        pedidoDAO.add(pedido_3)

        val materialPedido_3_2 = MaterialDePedidoDTO(null, material_areia, 50.0, 50.0, pedido_3)
        val materialPedido_3_1 = MaterialDePedidoDTO(null, material_tabua, 100.0, 100.0, pedido_3)
        materialPedidoDAO.add(materialPedido_3_1)
        materialPedidoDAO.add(materialPedido_3_2)
        pedido_3.materiais = arrayListOf(materialPedido_3_1, materialPedido_3_2)
        pedidoDAO.add(pedido_3)

        /* PEDIDO 4 */
        val situacaoHistorico_4_1 = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_4)
        val situacaoHistorico_4_2 = SituacaoHistoricoDTO(null, "Reprovado", Date(), pedido_4)
        situacaoHistoricoDAO.add(situacaoHistorico_4_1)
        situacaoHistoricoDAO.add(situacaoHistorico_4_2)
        pedido_4.historico_situacoes = arrayListOf(situacaoHistorico_4_1, situacaoHistorico_4_2)
        pedidoDAO.add(pedido_4)

        val materialPedido_4_1 = MaterialDePedidoDTO(null, material_boca_de_lobo, 9999.0, 0.0, pedido_4)
        materialPedidoDAO.add(materialPedido_4_1)
        pedido_4.materiais = arrayListOf(materialPedido_4_1)
        pedidoDAO.add(pedido_4)

        /* PEDIDO 5 */
        val situacaoHistorico_5_1 = SituacaoHistoricoDTO(null, "Em análise", Date(), pedido_5)
        val situacaoHistorico_5_2 = SituacaoHistoricoDTO(null, "Aprovado", Date(), pedido_5)
        val situacaoHistorico_5_3 = SituacaoHistoricoDTO(null, "Entrega Parcial 1", Date(), pedido_5)
        val situacaoHistorico_5_4 = SituacaoHistoricoDTO(null, "Entrega Parcial 2", Date(), pedido_5)
        situacaoHistoricoDAO.add(situacaoHistorico_5_1)
        situacaoHistoricoDAO.add(situacaoHistorico_5_2)
        situacaoHistoricoDAO.add(situacaoHistorico_5_3)
        situacaoHistoricoDAO.add(situacaoHistorico_5_4)
        pedido_5.historico_situacoes = arrayListOf(situacaoHistorico_5_1, situacaoHistorico_5_2, situacaoHistorico_5_3, situacaoHistorico_5_4)
        pedidoDAO.add(pedido_5)

        val materialPedido_5_1 = MaterialDePedidoDTO(null, material_arame_queimado, 100.0, 40.0, pedido_5)
        val materialPedido_5_2 = MaterialDePedidoDTO(null, material_areia, 100.0, 40.0, pedido_5)
        val materialPedido_5_3 = MaterialDePedidoDTO(null, material_boca_de_lobo, 100.0, 40.0, pedido_5)
        val materialPedido_5_4 = MaterialDePedidoDTO(null, material_tabua, 100.0, 40.0, pedido_5)
        materialPedidoDAO.add(materialPedido_5_1)
        materialPedidoDAO.add(materialPedido_5_2)
        materialPedidoDAO.add(materialPedido_5_3)
        materialPedidoDAO.add(materialPedido_5_4)
        pedido_5.materiais = arrayListOf(materialPedido_5_1, materialPedido_5_2, materialPedido_5_3, materialPedido_5_4)
        pedidoDAO.add(pedido_5)

        ///MOCK MATERIAIS
        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        val materialDeCadastro1 = MaterialDeCadastroDTO(1, material_arame_queimado, 100.0)
        val materialDeCadastro2 = MaterialDeCadastroDTO(2, material_areia, 100.0)
        val materialDeCadastro3 = MaterialDeCadastroDTO(3, material_boca_de_lobo, 100.0)
        val materialDeCadastro4 = MaterialDeCadastroDTO(4, material_tabua, 100.0)
        materialDeCadastroDAO.add(materialDeCadastro1)
        materialDeCadastroDAO.add(materialDeCadastro2)
        materialDeCadastroDAO.add(materialDeCadastro3)
        materialDeCadastroDAO.add(materialDeCadastro4)
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

        if(this.adapter == null) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista.layoutManager = layoutManager

            this.adapter = ListaPedidoAdapter(
                applicationContext,
                list,
                this,
                visualizarPedidoClickListener
            )
            rv_lista.adapter = adapter
        }
        else{
            this.adapter!!.updateAllItens(list)
        }
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

    override fun onResume() {
        super.onResume()
        viewModel!!.carregaListaPedido()
    }
}