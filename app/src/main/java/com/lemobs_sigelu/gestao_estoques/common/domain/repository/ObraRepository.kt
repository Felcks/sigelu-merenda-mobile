package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApiObras
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable


open class ObraRepository(private val api: RestApiObras): BaseRepository(), IObraRepository {

    override fun carregaListaObra(): Observable<List<Obra>> {

        return Observable.create { subscriber ->

            val callResponse = api.getObras()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val obras = response.body()!!.map {
                    Obra(it.id,
                        it.ordem_servico.codigo,
                        "",
                        "",
                        "",
                        it.ordem_servico.local_formatado,
                        "")
                }
                subscriber.onNext(obras)
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    override suspend fun carregaListaObra2(): List<Obra>? {

        val response = safeApiCall(
            call = {
                api.getObras2().await()
            },
            errorMessage = "Não foi possível carregar"
        )

        return response?.map {
            Obra(it.id,
                it.ordem_servico.codigo,
                "",
                "",
                "",
                it.ordem_servico.local_formatado,
                ""
            )
        }
    }
}