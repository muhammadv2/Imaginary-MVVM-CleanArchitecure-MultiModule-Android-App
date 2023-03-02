package com.developance.data.model

import com.developance.model.asEntity
import com.developance.model.data.CoverPhoto
import com.developance.model.data.ImageUrls
import com.developance.network.model.NetworkTopic
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
}