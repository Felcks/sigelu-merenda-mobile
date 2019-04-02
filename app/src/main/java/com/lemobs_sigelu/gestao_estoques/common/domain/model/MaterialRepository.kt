package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import io.reactivex.Observable

class MaterialRepository {

    val api: RestApi = RestApi()

    fun getMateriais(): Observable<List<Material>>{

        return Observable.create { subscriber ->

            val callResponse = api.getMateriais()
            val response = callResponse.execute()

            if (response.isSuccessful) {

                val materiais: List<Material> = response.body()!!.map {

                    val unidadeMedida = it.unidade_medida?.let {
                        UnidadeMedida(it.id,
                            it.nome ?: "",
                            it.sigla ?: "")
                    } ?: UnidadeMedida(0, "", "")

                    val nucleosComMaterial = it.disponibilidade_nucleos.map {
                        NucleoComMaterial(it.id,
                            it.nome ?: "",
                            it.quantidade ?: 0.0,
                            unidadeMedida.sigla)
                    }

                    Material(
                        it.id,
                        it.nome ?: "",
                        it.contratado ?: 0.0,
                        it.saldo ?: 0.0,
                        it.disponivel ?: 0.0,
                        unidadeMedida,
                        nucleosComMaterial)
                }

                subscriber.onNext(materiais)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}