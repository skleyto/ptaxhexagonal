// Camada de infraestrutura: implementação do acesso ao Bacen
package org.ptaxhexagonal.infrastructure

import com.splunk.logging.HttpEventCollectorLogbackAppender
import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.Ptax
import org.slf4j.LoggerFactory

class ConsultaPtaxBacenAdapter : ConsultaPtaxUseCase {
    private val logger = LoggerFactory.getLogger(ConsultaPtaxBacenAdapter::class.java)
    override fun consultarPtax(data: String): Ptax? {
        logger.info("Consultando PTAX para a data: $data")
        val url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='${data.replace("-", "/")}'"
        try {
            val connection = java.net.URL(url).openConnection() as java.net.HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            val responseCode = connection.responseCode
            logger.debug("Código de resposta HTTP: $responseCode")
            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                logger.debug("Resposta recebida: $response")
                val regex = "\"cotacaoCompra\":([0-9.]+)".toRegex()
                val match = regex.find(response)
                if (match != null) {
                    val valor = match.groupValues[1].toDouble()
                    logger.info("Valor encontrado: $valor")
                    return org.ptaxhexagonal.domain.Ptax(data, valor)
                } else {
                    logger.warn("Valor da cotação não encontrado na resposta.")
                }
            } else {
                logger.error("Falha ao consultar Bacen. Código HTTP: $responseCode")
            }
        } catch (e: Exception) {
            logger.error("Erro ao consultar Bacen: ${e.message}", e)
        }
        return null
    }
}
