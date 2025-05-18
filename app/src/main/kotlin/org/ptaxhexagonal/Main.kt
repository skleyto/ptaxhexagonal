import io.javalin.Javalin
import org.ptaxhexagonal.infrastructure.ConsultaPtaxBacenAdapter

fun main() {
    val app = Javalin.create().start(7000)
    val consultaPtax = ConsultaPtaxBacenAdapter()
    app.get("/ptax/{data}") { ctx ->
        val data = ctx.pathParam("data")
        val ptax = consultaPtax.consultarPtax(data)
        if (ptax != null) {
            ctx.json(mapOf("data" to ptax.data, "valor" to ptax.valor))
        } else {
            ctx.status(404).json(mapOf("erro" to "Cotação não encontrada"))
        }
    }
}