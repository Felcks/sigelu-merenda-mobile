package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_1_seleciona_envio

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCrSelecionaEnvioBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.FluxoInfo
import kotlinx.android.synthetic.main.activity_cr_seleciona_envio.*
import org.koin.android.ext.android.inject

class CRSelecionaEnvioActivity: AppCompatActivity(), ActivityDeFluxo{

    val viewModel: CRSelecionaEnvioViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cr_seleciona_envio)

        val binding: ActivityCrSelecionaEnvioBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_cr_seleciona_envio)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.listaEnvioResponse().observe(this, Observer<Response> { })

        val tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_envio)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel.carregaListaEnvio()
        }

        viewModel.getFluxo().setPassoAtual(1)
        viewModel.getFluxo().setMaximoPasso(FluxoInfo.RECEBIMENTO.maximoPassos)
        this.iniciaStepper()
    }

    private fun iniciaStepper(){
        top_stepper.setFluxo(viewModel.getFluxo())
        bottom_stepper.setFluxo(viewModel.getFluxo())

        top_stepper.atualiza()
        bottom_stepper.atualiza()

        bottom_stepper.setAnteriorOnClickListener { clicouAnterior() }
        bottom_stepper.setProximoOnClickListener { clicouProximo() }
    }

    override fun clicouProximo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clicouAnterior() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                if(response.data is List<*>){
                    if(response.data.isNotEmpty()){
                        if(response.data[0] is EnvioDTO){
                            iniciaListaEnvio(response.data as List<EnvioDTO>)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    private fun iniciaListaEnvio(listaEnvio: List<EnvioDTO>){


        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager

//        val adapter = ListaItemEstoqueAdapterSimples(applicationContext,
//            response.data as? List<ItemEstoqueDTO> ?: listOf(),
//            this,
//            viewModel.getIDsDeItemAdicionados())
//        rv_lista.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregaListaEnvio()
    }
}