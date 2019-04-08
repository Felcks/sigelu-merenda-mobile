package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_situacao_fragment.ListaSituacaoFragment

class VisualizarPedidoPageAdapter constructor(fm: FragmentManager, visualizarPedidoViewModel: VisualizarPedidoViewModel?) : FragmentStatePagerAdapter(fm) {

    var geralFragment = GeralFragment()
    var listaMaterialFragment = ListaMaterialFragment()
    val listaSituacaoFragment = ListaSituacaoFragment()

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> geralFragment
        1 -> listaMaterialFragment
        2 -> listaSituacaoFragment
        else -> GeralFragment()
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence =  when(position) {
        0 -> "Geral"
        1 -> "Materiais"
        2 -> "Histórico"
        else -> "Histórico"
    }

}