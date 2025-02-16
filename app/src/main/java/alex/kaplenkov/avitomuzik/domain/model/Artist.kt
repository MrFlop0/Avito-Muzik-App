package alex.kaplenkov.avitomuzik.domain.model

data class Artist(
    val id: Long,
    val link: String? = null,
    val name: String,
    val picture: String? = null,
    val pictureBig: String? = null,
    val pictureMedium: String? = null,
    val pictureSmall: String? = null,
    val pictureXl: String? = null,
    val trackList: String? = null,
)