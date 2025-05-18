package org.ptaxhexagonal.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class PtaxTest {
    @Test
    fun `deve criar entidade Ptax corretamente`() {
        val ptax = Ptax("2025-05-14", 5.25)
        assertEquals("2025-05-14", ptax.data)
        assertEquals(5.25, ptax.valor)
    }
}
