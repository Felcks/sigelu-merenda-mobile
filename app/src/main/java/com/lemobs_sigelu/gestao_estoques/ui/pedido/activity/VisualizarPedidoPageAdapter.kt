package com.lemobs_sigelu.gestao_estoques.ui.pedido.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lemobs_sigelu.gestao_estoques.ui.pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_envio_fragment.ListaEnvioFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_situacao_fragment.ListaSituacaoFragment

class VisualizarPedidoPageAdapter constructor(fm: FragmentManager, visualizarPedidoViewModel: VisualizarPedidoViewModel?) : FragmentStatePagerAdapter(fm) {

    var geralFragment = GeralFragment()
    var listaMaterialFragment = ListaMaterialFragment()
    val listaEnvioFragment = ListaEnvioFragment()
    val listaSituacaoFragment = ListaSituacaoFragment()

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> geralFragment
        1 -> listaMaterialFragment
        2 -> listaEnvioFragment
        3 -> listaSituacaoFragment
        else -> GeralFragment()
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence =  when(position) {
        0 -> "Geral"
        1 -> "Materiais"
        2 -> "Mov."
        3 -> "Situações"
        else -> "Situações"
    }

}