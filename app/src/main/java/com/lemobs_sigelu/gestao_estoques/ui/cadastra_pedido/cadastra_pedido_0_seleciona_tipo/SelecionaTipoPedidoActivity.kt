package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_tipo_pedido.*
import javax.inject.Inject

class SelecionaTipoPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: SelecionaTipoPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_tipo_pedido)
    }

    fun onFornecedorClicked(v: View){
        rb_obra.isChecked = false
        rb_nucleo.isChecked = false
        tv_proximo.text = "Próximo: Fornecedor"
    }

    fun onNucleoClicked(v: View){
        rb_obra.isChecked = false
        rb_fornecedor.isChecked = false
        tv_proximo.text = "Próximo: Núcleo"
    }

    fun onObraClicked(v: View){
        rb_nucleo.isChecked = false
        rb_fornecedor.isChecked = false
        tv_proximo.text = "Próximo: Obra"
    }


}
