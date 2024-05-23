import java.io.Serializable

data class Lectures(
    val lecturer: String,
    val title: String,
    val code: String,
    val description: String,
    val coverImage: String,
    val lat: Double,
    val long: Double
) : Serializable