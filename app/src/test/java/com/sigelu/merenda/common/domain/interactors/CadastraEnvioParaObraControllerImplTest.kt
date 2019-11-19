package com.sigelu.merenda.common.domain.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sigelu.merenda.common.domain.model.ItemEstoque
import com.sigelu.merenda.common.domain.model.NucleoModel
import com.sigelu.merenda.common.domain.model.Obra
import com.sigelu.merenda.common.domain.model.UsuarioModel
import com.sigelu.merenda.common.domain.repository.IObraRepository
import com.sigelu.merenda.common.domain.repository.ItemEstoqueRepository
import com.sigelu.merenda.exceptions.ObraNaoPermitidaException
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class CadastraEnvioParaObraControllerImplTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val itemEstoqueRepository: ItemEstoqueRepository = mockk()
    private val obraRepository: IObraRepository = mockk()
    private val nucleoModel: NucleoModel = mockk()
    private val usuarioModel: UsuarioModel = mockk()

    private fun defaultController(): CadastraEnvioParaObraControllerImpl =
        CadastraEnvioParaObraControllerImpl(obraRepository, itemEstoqueRepository, nucleoModel, usuarioModel)

    private var controller = defaultController()

    @Before
    fun setup(){

        every { nucleoModel.getNucleoID() } returns 1
        every { nucleoModel.getNucleoNome() } returns ""

        every { usuarioModel.getUsuarioID() } returns 1
        every { usuarioModel.getUsuarioNome() } returns ""
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
    fun `carrega listagem obra com obra de diferentes tipos`() {

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

    @Test(expected = ObraNaoPermitidaException::class)
    fun `seleciona obra invalida`() {

        val obraAndamento = spyk(mockk<Obra>()).apply  { id = 1; codigo = "" }

        val listaObra = listOf<Obra>(
            obraAndamento
        )

        controller = defaultController()

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra() } } returns listaObra
            controller.carregaListagemObra()

            controller.selecionaObra(2)
        }

    }

    @Test
    fun `seleciona obra valida`() {

        val obraAndamento = spyk(mockk<Obra>()).apply { id = 1; codigo = "" }

        val listaObra = listOf<Obra>(
            obraAndamento
        )

        controller = defaultController()

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra() } } returns listaObra
            controller.carregaListagemObra()

            controller.selecionaObra(1)
        }
    }

    @Test
    fun `carrega listagem item estoque`() {

        val listaItemEstoque = listOf<ItemEstoque>(
            mockk<ItemEstoque>(),
            mockk<ItemEstoque>()
        )

        val obraAndamento = spyk(mockk<Obra>()).apply { id = 1; codigo = "" }
        val listaObra = listOf<Obra>(
            obraAndamento
        )

        controller = defaultController()

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra() } } returns listaObra
            every { runBlocking { itemEstoqueRepository.carregaListaEstoque2() } } returns listaItemEstoque
            controller.carregaListagemObra()

            Assert.assertEquals(listaItemEstoque, controller.carregaListagemItemEstoque())
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

        assertEquals(0, controller.getIDsDeItemAdicionados().size)
        controller.confirmaSelecaoItens(listOf(), listOf())
        assertEquals(0, controller.getIDsDeItemAdicionados().size)
    }

    @Test
    fun `confirma selecao item 1 item`() {

        val obraAndamento = spyk(mockk<Obra>()).apply { id = 1; codigo = "" }

        val listaObra = listOf<Obra>(
            obraAndamento
        )

        controller = defaultController()

        runBlocking {
            every { runBlocking { obraRepository.carregaListaObra() } } returns listaObra
            controller.carregaListagemObra()
            controller.selecionaObra(1)

            val itemEstoqueID1 = mockk<ItemEstoque>()
            val listaItemEstoque = listOf<ItemEstoque>(itemEstoqueID1)
            every { itemEstoqueID1.id } returns 1
            every { runBlocking { itemEstoqueRepository.carregaListaEstoque2() } } returns listaItemEstoque

            assertEquals(0, controller.getIDsDeItemAdicionados().size)
            controller.confirmaSelecaoItens(listOf(itemEstoqueID1), listOf())
            assertEquals(1, controller.getIDsDeItemAdicionados().size)
        }
    }
}