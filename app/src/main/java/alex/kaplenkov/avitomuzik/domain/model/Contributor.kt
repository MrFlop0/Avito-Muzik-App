package alex.kaplenkov.avitomuzik.domain.model

data class Contributor(
    val id: Long,
    val link: String,
    val name: String,
    val picture: String,
    val pictureBig: String,
    val pictureMedium: String,
    val pictureSmall: String,
    val pictureXl: String,
    val radio: Boolean,
    val role: String,
    val share: String,
    val trackList: String,
)