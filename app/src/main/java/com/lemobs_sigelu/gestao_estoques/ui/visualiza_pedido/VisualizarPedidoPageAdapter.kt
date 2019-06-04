package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_envio_fragment.ListaEnvioFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.lista_situacao_fragment.ListaSituacaoFragment

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
        2 -> "Envios"
        3 -> "Situações"
        else -> "Situações"
    }

}