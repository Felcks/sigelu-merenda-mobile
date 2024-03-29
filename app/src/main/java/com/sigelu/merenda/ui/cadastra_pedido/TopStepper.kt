package com.sigelu.merenda.ui.cadastra_pedido

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.sigelu.merenda.R
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.sigelu.merenda.common.domain.interactors.Fluxo
import kotlinx.android.synthetic.main.stepper_top.view.*


class TopStepper: ConstraintLayout {

    private var fluxo: Fluxo? = null

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.stepper_top, this, false)
        val set = ConstraintSet()
        addView(view)

        set.clone(this)
        set.match(view, this)
    }

    fun setFluxo(fluxo: Fluxo){
        this.fluxo = fluxo
    }

    fun atualiza(){
        tv_passos.text = "Passo ${fluxo?.getPassoAtual()} de ${fluxo?.getMaximoPasso()}"

        if(fluxo?.getMaximoPasso() != fluxo?.getPassoAtual())
            tv_proximo.text = "Próximo: ${fluxo?.getTextoProximoPasso()}"
        else
            tv_proximo.text = ""
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