import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Serializable
data class Digimon(
    val name :String,
    val img :String,
    val level : String
)
fun main() {

    //  crear cliente http
    val client = HttpClient.newHttpClient()

    // crear solicitud
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://digimon-api.vercel.app/api/digimon"))
        .GET()
        .build()

    //  Enviar la solicitud con el cliente
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    // obtener string con datos
    val jsonBody = response.body()


    // Deserializar el JSON a una lista de objetos User
    val digimons: List<Digimon> = Json.decodeFromString(jsonBody)

    // Empezamos a consultar cosas interesantes de los digimons
    consultasinteresantes(digimons)

}

fun consultasinteresantes(digimons: List<Digimon>) {

    val NivelDigi = digimons.groupBy { it.level }.mapValues { it.value.size }
    println("Digimons por nivel:")
    NivelDigi.forEach { (nivel, cantidad) ->
        println("Hay $cantidad digimons de nivel $nivel")
    }

    println("----------------------")

    val letra1 = "A"
    val digimonsConLetra1 = digimons.count { it.name.startsWith(letra1, ignoreCase = true) }
    println("Hay $digimonsConLetra1 digimons que empiezan con la letra $letra1")

    println("----------------------")

    println("Introduce la letra con la que empieza el digimon : ")
    val letra2 = readln()
    val digimonsConLetra2 = digimons.count { it.name.startsWith(letra2, ignoreCase = true) }
    println("Hay $digimonsConLetra2 digimons que empiezan con la letra $letra2")

    println("----------------------")

    val randomdigimon = digimons.random()
    println("¡Foto de un Digimon aleatorio! ${randomdigimon.img}")

}
