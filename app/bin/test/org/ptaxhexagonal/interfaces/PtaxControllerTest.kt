package org.ptaxhexagonal.interfaces

import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.Ptax
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ConsultaPtaxUseCaseFake : ConsultaPtaxUseCase {
    override fun consultarPtax(data: String): Ptax? {
        return if (data == "2025-05-14") Ptax(data, 5.25) else null
    }
}

class PtaxControllerTest {
    private val controller = PtaxController(ConsultaPtaxUseCaseFake())

    @Test
    fun `deve retornar ptax para data conhecida`() {
        val ptax = controller.getPtax("2025-05-14")
        assertEquals(5.25, ptax?.valor)
    }

    @Test
    fun `deve retornar null para data desconhecida`() {
        val ptax = controller.getPtax("2020-01-01")
        assertNull(ptax)
    }
}
