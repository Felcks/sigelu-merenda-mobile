package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.PedidoSemOrigemOuDestinoException
import com.lemobs_sigelu.gestao_estoques.exceptions.PedidoSemTipoException
import com.lemobs_sigelu.gestao_estoques.mock
import com.lemobs_sigelu.gestao_estoques.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.Matchers.array
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.OngoingStubbing

class CadastraPedidoParaNucleoControllerTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val itemEstoqueRepository = mock<ItemEstoqueRepository>()
    private val pedidoRepository: PedidoRepository = mock<PedidoRepository>()
    private val obraRepository: ObraRepository = mock<ObraRepository>()

    private lateinit var controller: CadastraPedidoParaNucleoController

    @Before
    fun setup(){
//        itemEstoqueRepository = mock<ItemEstoqueRepository>()
//        pedidoRepository = mock<PedidoRepository>()
//        obraRepository = mock<ObraRepository>()
    }

    @Test(expected = PedidoSemTipoException::class)
    fun getTipoPedido_ComPedidoNulo() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.getTipoPedidoSelecionado()
    }

    @Test
    fun getPedido_SelecionandoTipos(){

        try{
            for(value in TipoPedido.values()){
                controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
                controller.selecionaTipoPedido(value)

                val tipoPedido = controller.getTipoPedidoSelecionado()
                assertEquals(value, tipoPedido)
            }
        }
        catch (e: java.lang.Exception){
            assertFalse(true)
        }
    }

    @Test(expected = PedidoSemOrigemOuDestinoException::class)
    fun confirmaDestinoDePedido_SemOrigemEDestino() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.confirmaDestinoDePedido(null, null)
    }

    @Test(expected = PedidoSemOrigemOuDestinoException::class)
    fun confirmaDestinoDePedido_SemOrigem() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        val destino: Local = mock<Local>()
        controller.confirmaDestinoDePedido(null, destino)
    }

    @Test(expected = PedidoSemOrigemOuDestinoException::class)
    fun confirmaDestinoDePedido_SemDestino() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        val origem: Local = mock<Local>()
        controller.confirmaDestinoDePedido(origem, null)
    }

    @Test(expected = Pedido.OrigemEDestinoIguaisException::class)
    fun confirmaDestinoDePedido_ComOrigemEDestinoIguais() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        val origem: Local = mock<Local>()
        val destino = origem

        controller.confirmaDestinoDePedido(origem, destino)
    }

    @Test
    fun connfirmaDestinoDePedido_ComInformacoesOk() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        val origem: Local = Local(0, "a", "a")
        val destino: Local = Local(1, "b", "b")
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        controller.confirmaDestinoDePedido(origem, destino)
        assertEquals(origem.nome, controller.getPedido()?.origem)
        assertEquals(origem.tipo, controller.getPedido()?.origemTipo)
        assertEquals(origem.id, controller.getPedido()?.origemID)

        assertEquals(destino.nome,controller.getPedido()?.destino)
        assertEquals(destino.tipo, controller.getPedido()?.destinoTipo)
        assertEquals(destino.id, controller.getPedido()?.destinoID)
    }
    @Test
    fun carregaListagemObra() {

        val listaObra =  listOf<Obra>(
            Obra(0, "", "", "", "", "", "")
        )

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        runBlocking {
            whenever(obraRepository.carregaListaObra2()).thenReturn(emptyList())

            assertEquals(controller.carregaListagemObra2(), emptyList<Obra>())
        }


        //val listaObra = listOf(mock<Obra>())

//        whenever(obraRepository.carregaListaObra()).thenReturn(Observable.just(listaObra))
//
//        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
//
//        val test = TestObserver<List<Obra>>()
//        controller.carregaListagemObra().subscribe(test)
//
//        test.assertComplete()






//        test.awaitTerminalEvent()
  //      test.assertNoErrors()
        //test.assertComplete()
        //assertThat(test.values(), hasItem(listaObra))



//        repository.getListagemProdutoObra(4).subscribe(testSubscriber)
//        testSubscriber.assertNoErrors()
//
//        testSubscriber.awaitTerminalEvent()
//        for (item in testSubscriber.onNextEvents[0]) {
//            assertNotNull(item.id)
//            assertNotNull(item.nome)
//            assertNotNull(item.diarioID)
//            assertNotNull(item.quantidade)
//            assertNotNull(item.unidadeMedida)
//        }
    }

    @Test
    fun selecionaItem() {

    }

    @Test
    fun carregaListagemItemEstoque() {
    }

    @Test
    fun getIDsDeItemAdicionados() {
    }

    @Test
    fun confirmaSelecaoItens() {
    }

    @Test
    fun confirmaCadastroItem() {
    }

    @Test
    fun getItensCadastrados() {
    }

    @Test
    fun removeItem() {
    }

    @Test
    fun cancelaPedido() {
    }

    @Test
    fun enviaPedido() {
    }

    @Test
    fun salvaRascunho() {
    }

    @Test
    fun getPedido() {
    }

    @Test
    fun salvaPedidoRascunho() {
    }
}