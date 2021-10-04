package com.assignment.giphyapp.data.model

import com.google.gson.annotations.SerializedName

//data class from api response

data class TrendingGifResponse(
    @SerializedName("data")
    val gifData: List<GifData>?,
    @SerializedName("meta")
    val meta: Meta?,
    @SerializedName("pagination")
    val pagination: Pagination?
)

data class GifData(
    val analytics: Analytics?=null,
    @SerializedName("analytics_response_payload")
    val analyticsResponsePayload: String?=null,
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String?=null,
    @SerializedName("bitly_url")
    val bitlyUrl: String?=null,
    @SerializedName("content_url")
    val contentUrl: String?=null,
    @SerializedName("embed_url")
    val embedUrl: String?=null,
    val id: String?=null,
    val images: Images?,
    @SerializedName("import_datetime")
    val importDatetime: String?=null,
    @SerializedName("is_sticker")
    val isSticker: Int?=null,
    val rating: String?=null,
    val slug: String?=null,
    val source: String?=null,
    @SerializedName("source_post_url")
    val sourcePostUrl: String?=null,
    @SerializedName("source_tld")
    val sourceTld: String?=null,
    val title: String?=null,
    @SerializedName("trending_datetime")
    val trendingDatetime: String?=null,
    val type: String?=null,
    val url: String?=null,
    @SerializedName("user")
    val userData: UserData?=null,
    val username: String?=null
)

data class Meta(
    @SerializedName("msg")
    val message: String?,
    @SerializedName("response_id")
    val responseId: String?,
    val status: Int?
)

data class Pagination(
    val count: Int?,
    val offset: Int?,
    @SerializedName("total_count")
    val totalCount: Int?
)

data class Analytics(
    val onclick: Onclick?,
    val onload: Onload?,
    val onsent: Onsent?
)

data class Images(
    @SerializedName("480w_still")
    val `480wStill`: WStill?=null,
    val downsized: Downsized?=null,
    val downsized_large: DownsizedLarge?=null,
    val downsized_medium: DownsizedMedium?=null,
    val downsized_small: DownsizedSmall?=null,
    val downsized_still: DownsizedStill?=null,
    @SerializedName("fixed_height")
    val fixedHeight: FixedHeight?,
    val fixed_height_downsampled: FixedHeightDownsampled?=null,
    val fixed_height_small: FixedHeightSmall?=null,
    val fixed_height_small_still: FixedHeightSmallStill?=null,
    val fixed_height_still: FixedHeightStill?=null,
    val fixed_width: FixedWidth?=null,
    val fixed_width_downsampled: FixedWidthDownsampled?=null,
    val fixed_width_small: FixedWidthSmall?=null,
    val fixed_width_small_still: FixedWidthSmallStill?=null,
    val fixed_width_still: FixedWidthStill?=null,
    val hd: Hd?=null,
    val looping: Looping?=null,
    val original: Original?=null,
    val original_mp4: OriginalMp4?=null,
    val original_still: OriginalStill?=null,
    val preview: Preview?=null,
    val preview_gif: PreviewGif?=null,
    val preview_webp: PreviewWebp?=null
)

data class UserData(
    val avatar_url: String?,
    val banner_image: String?,
    val banner_url: String?,
    val description: String?,
    val display_name: String?,
    val instagram_url: String?,
    val is_verified: Boolean?,
    val profile_url: String?,
    val username: String?,
    val website_url: String?
)

data class Onclick(
    val url: String?
)

data class Onload(
    val url: String?
)

data class Onsent(
    val url: String?
)

data class WStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class Downsized(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class DownsizedLarge(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class DownsizedMedium(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class DownsizedSmall(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val width: String?
)

data class DownsizedStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class FixedHeight(
    val height: String?=null,
    val mp4: String?=null,
    val mp4_size: String?=null,
    val size: String?=null,
    val url: String?,
    val webp: String?=null,
    val webp_size: String?=null,
    val width: String?=null
)

data class FixedHeightDownsampled(
    val height: String?,
    val size: String?,
    val url: String?,
    val webp: String?,
    val webp_size: String?,
    val width: String?
)

data class FixedHeightSmall(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val size: String?,
    val url: String?,
    val webp: String?,
    val webp_size: String?,
    val width: String?
)

data class FixedHeightSmallStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class FixedHeightStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class FixedWidth(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val size: String?,
    val url: String?,
    val webp: String?,
    val webp_size: String?,
    val width: String?
)

data class FixedWidthDownsampled(
    val height: String?,
    val size: String?,
    val url: String?,
    val webp: String?,
    val webp_size: String?,
    val width: String?
)

data class FixedWidthSmall(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val size: String?,
    val url: String?,
    val webp: String?,
    val webp_size: String?,
    val width: String?
)

data class FixedWidthSmallStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class FixedWidthStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class Hd(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val width: String?
)

data class Looping(
    val mp4: String?,
    val mp4_size: String?
)

data class Original(
    val frames: String?,
    val hash: String?,
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val size: String?,
    val url: String?,
    val webp: String?,
    val webp_size: String?,
    val width: String?
)

data class OriginalMp4(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val width: String?
)

data class OriginalStill(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class Preview(
    val height: String?,
    val mp4: String?,
    val mp4_size: String?,
    val width: String?
)

data class PreviewGif(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)

data class PreviewWebp(
    val height: String?,
    val size: String?,
    val url: String?,
    val width: String?
)