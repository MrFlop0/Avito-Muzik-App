package alex.kaplenkov.avitomuzik.domain.model


data class Album(
    val cover: String,
    val coverBig: String? = null,
    val coverMedium: String? = null,
    val coverSmall: String? = null,
    val coverXl: String? = null,
    val id: Long? = null,
    val link: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val trackList: String? = null,
)