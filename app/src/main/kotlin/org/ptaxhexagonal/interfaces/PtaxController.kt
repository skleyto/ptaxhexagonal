// Camada de interfaces: controlador REST (exemplo simplificado)
package org.ptaxhexagonal.interfaces

import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.PtaxDto

class PtaxController(private val consultaPtaxUseCase: ConsultaPtaxUseCase) {
    fun getPtax(data: String): PtaxDto? {
        return consultaPtaxUseCase.consultarPtax(data)
    }
}
