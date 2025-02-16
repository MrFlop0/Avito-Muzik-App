package alex.kaplenkov.avitomuzik.domain.model

data class SearchModel(
    val tracks: List<Track>,
    val total: Int,
)