package alex.kaplenkov.avitomuzik.data.api.dto


import alex.kaplenkov.avitomuzik.domain.model.Contributor
import com.google.gson.annotations.SerializedName

data class ContributorDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    @SerializedName("radio")
    val radio: Boolean,
    @SerializedName("role")
    val role: String,
    @SerializedName("share")
    val share: String,
    @SerializedName("tracklist")
    val trackList: String,
    @SerializedName("type")
    val type: String
)

fun ContributorDto.toContributor() =
    Contributor(
        id = id,
        link = link,
        name = name,
        picture = picture,
        pictureBig = pictureBig,
        pictureMedium = pictureMedium,
        pictureSmall = pictureSmall,
        pictureXl = pictureXl,
        radio = radio,
        role = role,
        share = share,
        trackList = trackList,
    )
