package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.Fluxo
import kotlinx.android.synthetic.main.stepper_bottom.view.*

class BottomStepper: ConstraintLayout {

    private var fluxo: Fluxo? = null
    private var bolinhas = mutableListOf<ImageView>()

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.stepper_bottom, this, false)
        val set = ConstraintSet()
        addView(view)

        set.clone(this)
        set.match(view, this)

        bolinhas.add(iv_bola_1)
        bolinhas.add(iv_bola_2)
        bolinhas.add(iv_bola_3)
        bolinhas.add(iv_bola_4)
        bolinhas.add(iv_bola_5)
        bolinhas.add(iv_bola_6)
        bolinhas.add(iv_bola_7)
    }

    fun setFluxo(fluxo: Fluxo){
        this.fluxo = fluxo
    }

    fun atualiza(){

        if(fluxo?.getMaximoPasso() != null) {

            for(i in fluxo!!.getPassoAtual()-1 until 7){
                bolinhas[i].visibility = View.VISIBLE
                bolinhas[i].setBackgroundResource(R.drawable.ic_flow_circle)
            }

            for (i in fluxo!!.getMaximoPasso() until 7){
                bolinhas[i].visibility = View.GONE
            }
        }

        bolinhas[(fluxo?.getPassoAtual() ?: 1) - 1].setBackgroundResource(R.drawable.ic_filled_circle)

        if(fluxo?.getPassoAtual() == fluxo?.getMaximoPasso()){
            tv_proximo.text = "CONCLUIR"
        }
        else{
            tv_proximo.text = "PRÓXIMO"
        }
    }

    fun setProximoOnClickListener(func: () -> Unit){
        ll_layout_proximo.setOnClickListener{ func() }
    }

    fun setAnteriorOnClickListener(func: () -> Unit){
        ll_layout_anterior.setOnClickListener{ func() }
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrSet: AttributeSet): super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int): super(context, attrSet, defStyleAttr)

    fun ConstraintSet.match(view: View, parentView: View) {
        this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
        this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
        this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
        this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
    }
}