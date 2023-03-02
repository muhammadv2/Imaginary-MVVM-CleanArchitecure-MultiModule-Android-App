package com.developance.data.model

import com.developance.model.asEntity
import com.developance.model.data.CoverPhoto
import com.developance.model.data.ImageUrls
import com.developance.model.data.Links
import com.developance.model.data.Photographer
import com.developance.network.model.NetworkPhoto
import com.developance.network.model.NetworkTopic
import com.developance.network.model.asDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NetworkEntityTest {

    @Test
    fun `network topic can be mapped to topic entity`() {
        val networkModel = NetworkTopic(
            id = "0",
            slug = "Test",
            title = "Test",
            description = "short description",
            totalPhotos = 5000,
            coverPhoto = CoverPhoto(ImageUrls(thumb = "url")),
        )
        val entity = networkModel.asEntity()

        assertThat(entity.id).matches("0")
        assertThat(entity.slug).matches("Test")
        assertThat(entity.title).matches("Test")
        assertThat(entity.description).matches("short description")
        assertThat(entity.coverPhoto).matches("url")
        assertThat(entity.totalPhotos).isEqualTo(5000L)

    }

    @Test
    fun network_photo_can_be_mapped_to_photo_entity() {
        val networkModel =
            NetworkPhoto(
                "2",
                "12/12/2020",
                2000,
                2000,
                "red",
                "BH2",
                2000,
                5000,
                description = null,
                Photographer(
                    "1",
                    "Muhammad",
                ),
                urls = ImageUrls(thumb = "URL"), location = null,
                links = Links("download", "download location")
            )
        val domain = networkModel.asDomain()

        assertThat(domain.id).matches("2")
        assertThat(domain.createdAt).matches("12/12/2020")
        assertThat(domain.color).matches("red")
        assertThat(domain.user.name).matches("Muhammad")
        assertThat(domain.urls.thumb).matches("URL")
        assertThat(domain.height).isEqualTo(2000)
        assertThat(domain.width).isEqualTo(2000)
        assertThat(domain.likes).isEqualTo(2000)
        assertThat(domain.downloads).isEqualTo(5000)
    }
}