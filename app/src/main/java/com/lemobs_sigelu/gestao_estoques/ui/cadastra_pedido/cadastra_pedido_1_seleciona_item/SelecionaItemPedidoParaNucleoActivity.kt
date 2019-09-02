package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido_original.*
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido_original.ll_loading
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido_original.rv_lista
import org.koin.android.ext.android.inject


class SelecionaItemPedidoParaNucleoActivity: AppCompatActivity(), TwoIntParametersClickListener {

    private val viewModel: SelecionaItemPedidoParaNucleoViewModel by inject()
    private var adapter : ListaItemEstoqueAdapterSimples? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido_original)

        viewModel.response.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.carregaListagemItem()
    }

    fun processResponse(response: Response){

        when(response.status){
            Status.LOADING -> {}
            Status.ERROR -> {
                ll_loading.visibility = View.GONE
                tv_error.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                ll_loading.visibility = View.GONE

                val layoutManager = LinearLayoutManager(applicationContext)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                rv_lista.layoutManager = layoutManager

                this.adapter = ListaItemEstoqueAdapterSimples(applicationContext,
                    response.data as List<ItemEstoque>,
                    this,
                    viewModel!!.getItensAdicionadosNucleo())
                rv_lista.adapter = adapter

            }
        }
    }

    override fun onClick(id: Int, pos: Int) {
        try{
            val adicionou = viewModel!!.selecionaItem(id)

            if(adicionou){
                adapter?.adicionaItem(pos)
            }
            else{
                adapter?.removeItem(pos)
            }
        }
        catch (e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }
}