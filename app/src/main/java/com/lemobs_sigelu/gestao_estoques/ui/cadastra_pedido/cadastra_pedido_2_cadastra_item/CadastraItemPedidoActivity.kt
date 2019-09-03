package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TwoIntParametersClickListener
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_confirma.ConfirmaCadastroPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_4_2_confirma_nucleo.ConfirmaCadastraPedidoNucleoActivity
import kotlinx.android.synthetic.main.activity_cadastra_item_pedido.*
import kotlinx.android.synthetic.main.activity_cadastra_item_pedido.ll_all
import kotlinx.android.synthetic.main.activity_cadastra_item_pedido.ll_layout_anterior
import kotlinx.android.synthetic.main.activity_cadastra_item_pedido.ll_layout_proximo
import org.koin.android.ext.android.inject

class CadastraItemPedidoActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: CadastraItemPedidoViewModel by inject()
    private var adapter: ListaItemEstoqueAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_item_pedido)

        val listaItemEnvio = viewModel.getItensSolicitados()
        if(listaItemEnvio.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista_material.layoutManager = layoutManager
            rv_lista_material.setItemViewCacheSize(listaItemEnvio.size)
            rv_lista_material.setHasFixedSize(true)

            this.adapter = ListaItemEstoqueAdapter(App.instance, listaItemEnvio, removerItemListener)
            rv_lista_material.adapter = adapter
        }
        else{
            tv_error.visibility = View.VISIBLE
        }

        ll_layout_anterior.setOnClickListener { clicouAnterior() }
        ll_layout_proximo.setOnClickListener { clicouProximo() }
    }


    private val removerItemListener = object: TwoIntParametersClickListener {
        override fun onClick(id: Int, position: Int) {
            try{
                viewModel.removeItem(id)
                adapter?.removeItem(position)
                tv_total_material.text = "(${viewModel!!.getItensSolicitados().size})"

                if(viewModel.getItensSolicitados().isEmpty())
                    tv_error.visibility = View.VISIBLE
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun clicouProximo(){

        try {
            viewModel.confirmaCadastroMaterial(this.adapter?.getListaValoresItemEnvio() ?: listOf())

            val intent = Intent(this, ConfirmaCadastroPedidoActivity::class.java)
            startActivity(intent)
        }
        catch (e: NenhumItemSelecionadoException){
            Snackbar.make(ll_all, "Selecione pelo menos um item.", Snackbar.LENGTH_SHORT).show()
        }
        catch (e: CampoNaoPreenchidoException){
            Snackbar.make(ll_all, "Preencha a quantidade.", Snackbar.LENGTH_SHORT).show()
        }
        catch(e: ValorMenorQueZeroException){
            Snackbar.make(ll_all, "Preencha a quantidade com um valor maior que zero.", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun clicouAnterior(){
        this.onBackPressed()
    }
}