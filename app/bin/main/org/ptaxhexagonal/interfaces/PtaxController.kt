// Camada de interfaces: controlador REST (exemplo simplificado)
package org.ptaxhexagonal.interfaces

import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.Ptax

class PtaxController(private val consultaPtaxUseCase: ConsultaPtaxUseCase) {
    fun getPtax(data: String): Ptax? {
        return consultaPtaxUseCase.consultarPtax(data)
    }
}
