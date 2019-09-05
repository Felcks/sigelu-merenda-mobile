package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import com.lemobs_sigelu.gestao_estoques.mock
import com.lemobs_sigelu.gestao_estoques.whenever
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
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

    private val itemEstoqueRepository = mockk<ItemEstoqueRepository>()
    private val pedidoRepository: PedidoRepository = mockk<PedidoRepository>()
    private val obraRepository: ObraRepository = mockk<ObraRepository>()

    private lateinit var controller: CadastraPedidoParaNucleoController

    @Before
    fun setup(){}

    @Test(expected = PedidoSemTipoException::class)
    fun `pega tipo de pedido de pedido nulo`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.getTipoPedidoSelecionado()
    }


    @Test
    fun `seleciona todos os tipos e faz get no tipo`(){

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
    fun `confirma destino de pedido sem origem e destino`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.confirmaDestinoDePedido()
    }

    @Test(expected = PedidoSemOrigemOuDestinoException::class)
    fun `confirma destino de pedido sem origem`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        val destino: Local = mock<Local>()
        controller.confirmaDestinoDePedido(null, destino)
    }

    @Test(expected = PedidoSemOrigemOuDestinoException::class)
    fun `confirma destino de pedido sem destino`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        val origem: Local = mock<Local>()
        controller.confirmaDestinoDePedido(origem, null)
    }

    @Test(expected = Pedido.OrigemEDestinoIguaisException::class)
    fun `confirma destino com origem e destino iguais`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        val origem: Local = mock<Local>()
        val destino = origem

        controller.confirmaDestinoDePedido(origem, destino)
    }

    @Test
    fun `confirma destino com informacoes validas`() {

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
    fun `carrega listagem de obra vazia`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra2() } } returns emptyList()

            assertEquals(emptyList<Obra>(), controller.carregaListagemObra2())
        }
    }

    @Test
    fun `carrega listagem obra com obra de diferentes tipos`(){

        val obraAndamento = mockk<Obra>()
        val obraPlanejada = mockk<Obra>()
        val obraParalisada = mockk<Obra>()
        val obraCancelada = mockk<Obra>()
        val obraConcluida = mockk<Obra>()

        val listaObra = listOf<Obra>(
            obraAndamento,
            obraPlanejada,
            obraParalisada,
            obraCancelada,
            obraConcluida
        )

        every { obraAndamento.situacao } returns "Em Andamento"
        every { obraPlanejada.situacao } returns "Planejada"
        every { obraParalisada.situacao } returns "Paralisada"
        every { obraCancelada.situacao } returns "Cancelada"
        every { obraConcluida.situacao } returns "Concluida"

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra2() } } returns listaObra

            assertEquals(listaObra.size, controller.carregaListagemObra2()?.size)
        }
    }

    @Test(expected = PedidoNaoCriadoException::class)
    fun `seleciona item para pedido nao criado`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.veriricaSeItemJaEstaAdicionado(1)
    }

    @Test
    fun `seleciona 1 item para pedido criado`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        assertEquals(true, controller.veriricaSeItemJaEstaAdicionado(1))
    }

    @Test
    fun `seleciona 2 itens diferentes para pedido criado`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        assertEquals(true, controller.veriricaSeItemJaEstaAdicionado(1))
        assertEquals(true, controller.veriricaSeItemJaEstaAdicionado(2))
    }

    @Test
    fun `carrega listagem item estoque`() {

        val listaItemEstoque = listOf<ItemEstoque>(
            mockk<ItemEstoque>(),
            mockk<ItemEstoque>()
        )

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)


        runBlocking {
            every { runBlocking { itemEstoqueRepository.carregaListaEstoque2() } } returns listaItemEstoque

            assertEquals(listaItemEstoque, controller.carregaListagemItemEstoque2())
        }
    }

    @Test
    fun `get id de itens ja adicionados vazio`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }

    @Test
    fun `confirma selecao nenhum item e resgata lista fazia`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(), listOf())
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }
    
    @Test
    fun `confirma selecao item 1 item`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoqueID1 = mockk<ItemEstoque>()
        every { itemEstoqueID1.id } returns 1

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
        assertEquals(1, controller.getIDsDeItemAdicionados().size)
    }

    @Test
    fun `confirma selecao de item remove 1 item inexistente`(){

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoqueID1 = mockk<ItemEstoque>()
        every { itemEstoqueID1.id } returns 1

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(), listOf(itemEstoqueID1))
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }

    @Test
    fun `confirma selecao de item adiciona 1 item e remove esse item`(){

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoqueID1 = mockk<ItemEstoque>()
        every { itemEstoqueID1.id } returns 1

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
        assertEquals(1, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(), listOf(itemEstoqueID1))
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }

    @Test
    fun `adiciona 2 item e remove 1 deles`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoqueID1 = spyk(mockk<ItemEstoque>()).apply { id = 1 }
        val itemEstoqueID2 = spyk(mockk<ItemEstoque>()).apply { id = 2 }

        assertEquals(0, controller.getIDsDeItemAdicionados().size)

        controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
        assertEquals(1, controller.getIDsDeItemAdicionados().size)

        controller.confirmaSelecaoItens(listOf(itemEstoqueID2), listOf())
        assertEquals(2, controller.getIDsDeItemAdicionados().size)

        controller.confirmaSelecaoItens(listOf(), listOf(itemEstoqueID1))
        assertEquals(1, controller.getIDsDeItemAdicionados().size)
    }

    @Test(expected = PedidoNaoCriadoException::class)
    fun `confirma cadastro com pedido nulo`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)

        controller.confirmaCadastroItem(listOf())
    }

    @Test
    fun `confirma cadastro com lista vazia`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        controller.confirmaCadastroItem(listOf())

        assertEquals(0, controller.getItensCadastrados().size)
    }

    @Test(expected = Exception::class)
    fun `confirma cadastro com 1 numero sem item`(){

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        controller.confirmaCadastroItem(listOf(0.0))
    }

    @Test
    fun `confirma cadastro com 1 numero com item e valor valido`(){

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoque = spyk(mockk<ItemEstoque>())
        controller.confirmaSelecaoItens(listOf(itemEstoque), listOf())

        controller.confirmaCadastroItem(listOf(2.0))

        assertEquals(2.0, controller.getItensCadastrados()[0].quantidadeRecebida)
    }

    @Test(expected = ValorMenorQueZeroException::class)
    fun `confirma cadastro com 1 numero com item e valor zerado`(){

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        controller.confirmaSelecaoItens(listOf(mockk<ItemEstoque>()), listOf())

        controller.confirmaCadastroItem(listOf(0.0))
    }

    @Test
    fun `get itens cadastrados sem nenhum item`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        assertEquals(0, controller.getItensCadastrados().size)
    }

    @Test
    fun `get itens cadastrados com um item cadastrado`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoque = spyk(mockk<ItemEstoque>()).apply { id = 33; codigo = "codigo"; nomeAlternativo = "nome"  }
        controller.confirmaSelecaoItens(listOf(itemEstoque), listOf())

        assertEquals(1, controller.getItensCadastrados().size)
        assertEquals(33, controller.getItensCadastrados()[0].id)
        assertEquals("codigo", controller.getItensCadastrados()[0].codigo)
        assertEquals("nome", controller.getItensCadastrados()[0].nomeAlternativo)
    }


    @Test(expected = Exception::class)
    fun `remove item inexistente`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        controller.removeItem(0)
    }

    @Test
    fun `remove item existente`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val item = spyk(mockk<ItemEstoque>()).apply { id = 1 }
        controller.confirmaSelecaoItens(listOf(item), listOf())
        assertEquals(1, controller.getItensCadastrados().size)

        controller.removeItem(1)
        assertEquals(0, controller.getItensCadastrados().size)

    }

    @Test
    fun `cancela pedido`() {

        controller = CadastraPedidoParaNucleoController(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        assertNotNull(controller.getPedido())
        controller.cancelaPedido()
        assertNull(controller.getPedido())
    }
}