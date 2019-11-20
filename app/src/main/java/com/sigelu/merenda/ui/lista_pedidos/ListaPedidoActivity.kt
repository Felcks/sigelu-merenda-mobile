package com.sigelu.merenda.ui.lista_pedidos

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.snackbar.Snackbar
import com.sigelu.merenda.BuildConfig
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.*
import com.sigelu.merenda.common.domain.model.accounts.CarregaDados
import com.sigelu.merenda.common.domain.model.accounts.DataHolder
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.extensions_constants.esconderTeclado
import com.sigelu.merenda.extensions_constants.reiniciarActivity
import com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_0_seleciona_obra.CESelecionaObraActivity
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo.SelecionaTipoPedidoActivity
import com.sigelu.merenda.ui.estoque.EstoqueActivity
import com.sigelu.merenda.ui.pedido.activity.VisualizarPedidoActivity
import com.sigelu.merenda.utils.AlertDialogView
import com.sigelu.merenda.utils.AppSharedPreferences
import com.sigelu.merenda.utils.ControladorFonte
import com.sigelu.merenda.utils.ControladorLogout
import com.sigelu.core.lib.DialogUtil
import com.sigelu.merenda.databinding.ActivityListaPedidoBinding
import com.sigelu.merenda.exceptions.SemPermissaoException
import com.sigelu.merenda.extensions_constants.verificaPermissao
import com.sigelu.utils.menu_lateral.PrepararMenuLateral
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_pedido.*
import javax.inject.Inject

class ListaPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaPedidoViewModelFactory
    var viewModel: ListaPedidoViewModel? = null

    var adapter: ListaPedidoAdapter? = null
    /* Fonte */
    private var controladorFonte: ControladorFonte? = null
    /* Controlador logout */
    private var controladorLogout: ControladorLogout? = null
    /* Pesquisa */
    private var pesquisando = false
    /* Menu */
    private var menu: Menu? = null

    var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val binding: ActivityListaPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_lista_pedido)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        this.iniciarAdapter(listOf())

        menu_item_cadastrar_pedido.setOnClickListener {

            try {
                verificaPermissao(PermissaoModel.incluirRM) {
                    val intent = Intent(this, SelecionaTipoPedidoActivity::class.java)
                    startActivity(intent)
                }
            }
            catch (t: Throwable){
                Snackbar.make(ll_all, "Sem permissão para fazer RM.", Snackbar.LENGTH_LONG).show()
            }
        }

        /* Botão de cadastrar envio herdado do logística
        menu_item_cadastra_envio.setOnClickListener {

            try{
                verificaPermissao(PermissaoModel.incluirEnvio){
                    val intent = Intent(this, CESelecionaObraActivity::class.java)
                    startActivity(intent)
                }
            }
            catch (t: Throwable){
                Snackbar.make(ll_all, "Sem permissão para fazer envio.", Snackbar.LENGTH_LONG).show()
            }
        }
        */

        tvErro = ll_erro.findViewById(R.id.tv_erro)
        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel!!.carregaListaPedido()
        }

        this.controladorFonte = ControladorFonte(this)
        this.controladorLogout = ControladorLogout(this)
        this.iniciaCampoBusca()
        this.prepararMenuLateral()
    }

    private fun iniciaCampoBusca(){

        tv_busca.inputType = InputType.TYPE_CLASS_TEXT

        tv_busca.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                filtraBusca(tv_busca.text.toString())

                if(tv_busca.text.isNotEmpty()){
                    img_busca.setBackgroundResource(R.drawable.ic_cancel_gray)
                }
                else{
                    img_busca.setBackgroundResource(R.drawable.ic_magn_glass)
                }
                pesquisando = tv_busca.text.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        img_busca.setOnClickListener {

            if(pesquisando){
                limpaBusca()
            }

            tv_busca.esconderTeclado()
        }

        tv_busca.setOnKeyListener { _, keyCode, event ->
            if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                tv_busca.esconderTeclado()
            }
            false
        }
    }

    private fun filtraBusca(text: String) {
        adapter?.filtraBusca(text)
    }

    private fun limpaBusca(){
        tv_busca.text.clear()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> {
                if(response.error is SemPermissaoException){
                    tvErro?.text = response.error.message
                }
                else{
                    tvErro?.text = resources.getString(R.string.erro_carrega_lista_pedido)
                }
            }
            Status.EMPTY_RESPONSE -> {
                tvErro?.text = resources.getString(R.string.erro_lista_pedido_vazia)
            }
        }
    }

    private fun renderLoadingState() {
        viewModel!!.isError.set(false)
    }

    private fun renderDataState(result: Any?) {
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido2>)
        }
    }


    private fun iniciarAdapter(list: List<Pedido2>){

        if(this.adapter == null) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista.layoutManager = layoutManager

            this.adapter = ListaPedidoAdapter(
                applicationContext,
                list,
                envioOuRecebimentoClickListener,
                visualizarPedidoClickListener
            )
            rv_lista.adapter = adapter
        }
        else{
            this.adapter!!.updateAllItens(list)
        }
    }

    private val visualizarPedidoClickListener = object : OneIntParameterClickListener {
        override fun onClick(id: Int) {

            try{
                verificaPermissao(PermissaoModel.visualizarRM){
                    viewModel!!.armazenaPedidoNoFluxo(id)
                    val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
                    startActivity(intent)
                }

            }
            catch (t: Throwable){
                Snackbar.make(ll_all, "Sem permissão para visualizar pedido.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private val envioOuRecebimentoClickListener = object : OneIntParameterClickListener {
        override fun onClick(id: Int) {

            val pedido = adapter?.getPedidoById(id)
            viewModel!!.armazenaPedidoNoFluxo(id)

            val intent = when(pedido?.getTipoMovimento()){
//                TipoMovi.MEU_NUCLEO_PARA_OUTRO_NUCLEO -> Intent(applicationContext, CadastraEnvioActivity::class.java)
//                TipoPedido.MEU_NUCLEO_PARA_OBRA -> Intent(applicationContext, CadastraEnvioActivity::class.java)
//                TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO -> Intent(applicationContext, CadastraRecebimentoSESelecionaItemActivity::class.java)
//                TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO -> Intent(applicationContext, SelecionaEnvioRecebimentoActivity::class.java)
                else -> Intent(applicationContext, ListaPedidoActivity::class.java)
            }

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_lista_pedido, menu)

        val usuario = AppSharedPreferences.getUserName(applicationContext)
        menu?.findItem(R.id.btn_usuario)?.title =  "$usuario - ${BuildConfig.VERSION_NAME}"
        menu?.findItem(R.id.btn_usuario)?.isEnabled =  false
        this.menu = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.btn_update -> {
                limpaBusca()
                viewModel!!.carregaListaPedido()
                true
            }
            R.id.btn_visualiza_estoque -> {
                try{
                    verificaPermissao(PermissaoModel.listarEstoque) {
                        val intent = Intent(this, EstoqueActivity::class.java)
                        startActivity(intent)
                    }
                }
                catch (t: Throwable){
                    Snackbar.make(ll_all, "Sem permissão para visualizar estoque.", Snackbar.LENGTH_LONG).show()
                }
                true
            }
            R.id.btn_aumentar_fonte -> {
                this.controladorFonte?.trocarTamanhoFonte()
                this.reiniciarActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        menu?.findItem(R.id.btn_aumentar_fonte)?.title = this.controladorFonte?.getTextoTrocarFonte()
        return super.onPrepareOptionsMenu(menu)
    }

    /* Funções de deslogar o usuário */
    var progressDialog: ProgressDialog? = null
    private fun showProgressoDeslogandoUsuario(){
        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Deslogando",
            "Por favor, espere...")
    }

    fun showDuvidaDeslogarUsuario(){
        val alertDialogView = DialogUtil.buildAlertDialogSimNao(this,
            "Aviso",
            "Deseja sair do Sigelu Merenda?",
            {
                this.showProgressoDeslogandoUsuario()
                try{
                    backToLauncher()
                }
                catch (e: Exception){
                    this.showFalhaDeslogouUsuario(e.toString())
                }
            },
            {

            },
            cancelavel = true)

        alertDialogView.show()
    }

    var errorDialog: AlertDialogView? = null
    private fun showFalhaDeslogouUsuario(mensagem: String){

        progressDialog?.dismiss()
        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Falha no logout.",
            {

            },
            true)

        this.errorDialog?.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.carregaListaPedido()
    }

    override fun onBackPressed() {
        showDuvidaDeslogarUsuario()
    }

    private fun prepararMenuLateral() {
        val prepararMenuLateral = PrepararMenuLateral(PreparadorConcreto(this))
        prepararMenuLateral.prepara()
    }

    private fun backToLauncher(){
        CarregaDados.limpar()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DataHolder.getSchemeLauncher()))
        intent.action = Intent.ACTION_VIEW
        startActivity(intent)
        finish()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}