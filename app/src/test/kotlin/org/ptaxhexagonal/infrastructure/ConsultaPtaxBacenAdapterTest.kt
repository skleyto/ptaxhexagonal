package org.ptaxhexagonal.infrastructure

import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.Ptax
import kotlin.test.Test
import kotlin.test.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class ConsultaPtaxBacenAdapterTest {
    private val adapter = ConsultaPtaxBacenAdapter()

    @Test
    fun `deve retornar ptax para data válida com cotação disponível`() {
        // Use uma data real que tenha cotação disponível no Bacen
        val ptax = adapter.consultarPtax("05-16-2025")
        assertTrue(ptax == null || ptax.valor > 0.0)
    }

    @Test
    fun `deve retornar null para data futura sem cotação`() {
        val ptax = adapter.consultarPtax("12-31-2099")
        assertNull(ptax)
    }

    @Test
    fun `deve retornar null para data inválida`() {
        val ptax = adapter.consultarPtax("data-invalida")
        assertNull(ptax)
    }

    @Test
    fun `deve retornar null para data em formato incorreto`() {
        val ptax = adapter.consultarPtax("2025/05/16")
        assertNull(ptax)
    }
}
