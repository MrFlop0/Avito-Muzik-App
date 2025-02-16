package alex.kaplenkov.avitomuzik.domain.model

data class Track(
    val album: Album,
    val artist: Artist,
    val contributors: List<Contributor>?,
    val duration: Int,
    val explicitContentLyrics: Int,
    val explicitLyrics: Boolean,
    val id: Long,
    val link: String,
    val preview: String,
    val title: String,
    val titleShort: String,
)