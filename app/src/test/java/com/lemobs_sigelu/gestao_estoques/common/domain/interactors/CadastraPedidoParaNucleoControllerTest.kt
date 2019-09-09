package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.IObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class CadastraPedidoParaNucleoControllerTest: KoinTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val itemEstoqueRepository: ItemEstoqueRepository= mockk()
    private val pedidoRepository: PedidoRepository = mockk()
    private val obraRepository: IObraRepository = mockk()
    private val nucleoModel: NucleoModel = mockk()

    private fun defaultController(): CadastraPedidoControllerImpl = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository, nucleoModel)
    private var controller = defaultController()

    @Before
    fun setup(){


    }

    @After
    fun close(){
        stopKoin()
    }

    @Test(expected = PedidoSemTipoException::class)
    fun `pega tipo de pedido de pedido nulo`() {

        controller.getTipoPedidoSelecionado()
    }


    @Test(expected = TipoPedidoNaoPermitido::class)
    fun `confirma destino para tipos nao permitidos`() {

        for(value in TipoPedido.values()){

            if(value != TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO && value != TipoPedido.FORNECEDOR_PARA_OBRA)
                controller.confirmaDestinoDePedido(value)
        }
    }

    @Test(expected = TipoPedidoNaoPermitido::class)
    fun `confirma destino para tipo fornecedor-obra sem obra`() {

        controller.confirmaDestinoDePedido(TipoPedido.MEU_NUCLEO_PARA_OBRA)
    }

    @Test
    fun `confirma destino para tipo fornecedor-nucleo`() {

        every { nucleoModel.getNucleoID() } returns 1
        every { nucleoModel.getNucleoNome() } returns "Núcleo nome"
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
    }

    @Test
    fun `confirma destino para obra corretamente`() {

        val local = spyk(mockk<Local>()).apply { id = 1; nome = "n"; tipo = "t"  }
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_OBRA, local)
    }



    @Test
    fun `carrega listagem de obra vazia`() {

        controller = defaultController()

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra() } } returns emptyList()

            assertEquals(emptyList<Obra>(), controller.carregaListagemObra())
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

        controller = defaultController()

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra() } } returns listaObra

            assertEquals(listaObra.size, controller.carregaListagemObra()?.size)
        }
    }

    @Test(expected = PedidoNaoCriadoException::class)
    fun `seleciona item para pedido nao criado`() {

        controller = defaultController()
        controller.veriricaSeItemJaEstaAdicionado(1)
    }

    @Test
    fun `seleciona 1 item para pedido criado`() {

        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_OBRA)

        assertEquals(true, controller.veriricaSeItemJaEstaAdicionado(1))
    }

    @Test
    fun `seleciona 2 itens diferentes para pedido criado`() {

        controller = defaultController()
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_OBRA)

        assertEquals(true, controller.veriricaSeItemJaEstaAdicionado(1))
        assertEquals(true, controller.veriricaSeItemJaEstaAdicionado(2))
    }

    @Test
    fun `carrega listagem item estoque`() {

        val listaItemEstoque = listOf<ItemEstoque>(
            mockk<ItemEstoque>(),
            mockk<ItemEstoque>()
        )

        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)


        runBlocking {
            every { runBlocking { itemEstoqueRepository.carregaListaEstoque2() } } returns listaItemEstoque

            assertEquals(listaItemEstoque, controller.carregaListagemItemEstoque())
        }
    }

    @Test
    fun `get id de itens ja adicionados vazio`() {

        controller = defaultController()
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }


    @Test
    fun `confirma selecao nenhum item e resgata lista fazia`() {

        controller = defaultController()
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(), listOf())
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }

    @Test
    fun `confirma selecao item 1 item`() {

        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
        controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)

        val itemEstoqueID1 = mockk<ItemEstoque>()
        every { itemEstoqueID1.id } returns 1

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
        assertEquals(1, controller.getIDsDeItemAdicionados().size)
    }


//    @Test
//    fun `confirma selecao de item remove 1 item inexistente`(){
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        val itemEstoqueID1 = mockk<ItemEstoque>()
//        every { itemEstoqueID1.id } returns 1
//
//        assertEquals(0, controller.getIDsDeItemAdicionados().size)
//        controller.confirmaSelecaoItens(listOf(), listOf(itemEstoqueID1))
//        assertEquals(0, controller.getIDsDeItemAdicionados().size)
//    }
//
//    @Test
//    fun `confirma selecao de item adiciona 1 item e remove esse item`(){
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        val itemEstoqueID1 = mockk<ItemEstoque>()
//        every { itemEstoqueID1.id } returns 1
//
//        assertEquals(0, controller.getIDsDeItemAdicionados().size)
//        controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
//        assertEquals(1, controller.getIDsDeItemAdicionados().size)
//        controller.confirmaSelecaoItens(listOf(), listOf(itemEstoqueID1))
//        assertEquals(0, controller.getIDsDeItemAdicionados().size)
//    }
//
//    @Test
//    fun `adiciona 2 item e remove 1 deles`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        val itemEstoqueID1 = spyk(mockk<ItemEstoque>()).apply { id = 1 }
//        val itemEstoqueID2 = spyk(mockk<ItemEstoque>()).apply { id = 2 }
//
//        assertEquals(0, controller.getIDsDeItemAdicionados().size)
//
//        controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
//        assertEquals(1, controller.getIDsDeItemAdicionados().size)
//
//        controller.confirmaSelecaoItens(listOf(itemEstoqueID2), listOf())
//        assertEquals(2, controller.getIDsDeItemAdicionados().size)
//
//        controller.confirmaSelecaoItens(listOf(), listOf(itemEstoqueID1))
//        assertEquals(1, controller.getIDsDeItemAdicionados().size)
//    }
//
//    @Test(expected = PedidoNaoCriadoException::class)
//    fun `confirma cadastro com pedido nulo`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//
//        controller.confirmaCadastroItem(listOf())
//    }
//
//    @Test
//    fun `confirma cadastro com lista vazia`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        controller.confirmaCadastroItem(listOf())
//
//        assertEquals(0, controller.getItensCadastrados().size)
//    }
//
//    @Test(expected = Exception::class)
//    fun `confirma cadastro com 1 numero sem item`(){
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        controller.confirmaCadastroItem(listOf(0.0))
//    }
//
//    @Test
//    fun `confirma cadastro com 1 numero com item e valor valido`(){
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        val itemEstoque = spyk(mockk<ItemEstoque>())
//        controller.confirmaSelecaoItens(listOf(itemEstoque), listOf())
//
//        controller.confirmaCadastroItem(listOf(2.0))
//
//        assertEquals(2.0, controller.getItensCadastrados()[0].quantidadeRecebida)
//    }
//
//    @Test(expected = ValorMenorQueZeroException::class)
//    fun `confirma cadastro com 1 numero com item e valor zerado`(){
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        controller.confirmaSelecaoItens(listOf(mockk<ItemEstoque>()), listOf())
//
//        controller.confirmaCadastroItem(listOf(0.0))
//    }
//
//    @Test
//    fun `get itens cadastrados sem nenhum item`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        assertEquals(0, controller.getItensCadastrados().size)
//    }
//
//    @Test
//    fun `get itens cadastrados com um item cadastrado`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        val itemEstoque = spyk(mockk<ItemEstoque>()).apply { id = 33; codigo = "codigo"; nomeAlternativo = "nome"  }
//        controller.confirmaSelecaoItens(listOf(itemEstoque), listOf())
//
//        assertEquals(1, controller.getItensCadastrados().size)
//        assertEquals(33, controller.getItensCadastrados()[0].id)
//        assertEquals("codigo", controller.getItensCadastrados()[0].codigo)
//        assertEquals("nome", controller.getItensCadastrados()[0].nomeAlternativo)
//    }
//
//
//    @Test(expected = Exception::class)
//    fun `remove item inexistente`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        controller.removeItem(0)
//    }
//
//    @Test
//    fun `remove item existente`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        val item = spyk(mockk<ItemEstoque>()).apply { id = 1 }
//        controller.confirmaSelecaoItens(listOf(item), listOf())
//        assertEquals(1, controller.getItensCadastrados().size)
//
//        controller.removeItem(1)
//        assertEquals(0, controller.getItensCadastrados().size)
//
//    }
//
//    @Test
//    fun `cancela pedido`() {
//
//        controller = CadastraPedidoControllerImpl(itemEstoqueRepository, pedidoRepository, obraRepository)
//        controller.selecionaTipoPedido(TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO)
//
//        assertNotNull(controller.getPedido())
//        controller.cancelaPedido()
//        assertNull(controller.getPedido())
//    }
}