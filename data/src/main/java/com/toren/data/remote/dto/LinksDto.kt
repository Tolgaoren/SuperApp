package com.toren.data.remote.dto

import com.toren.domain.model.rocket_api.Flickr
import com.toren.domain.model.rocket_api.Links
import com.toren.domain.model.rocket_api.Patch

data class LinksDto(
    val article: String,
    val flickr: Flickr,
    val patch: Patch,
    val presskit: Any,
    val reddit: Reddit,
    val webcast: String,
    val wikipedia: String,
    val youtube_id: String
)


fun LinksDto.toLinks(): Links {
    return Links(
        article = article,
        flickr = flickr,
        patch = patch,
        webcast = webcast,
        wikipedia = wikipedia,
        youtube_id = youtube_id
    )
}