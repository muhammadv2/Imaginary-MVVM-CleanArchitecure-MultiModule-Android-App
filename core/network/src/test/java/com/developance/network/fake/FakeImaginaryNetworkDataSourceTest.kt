package com.developance.network.fake

import com.developance.model.data.*
import com.developance.network.model.NetworkPhoto
import com.developance.network.model.NetworkTopic
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeImaginaryNetworkDataSourceTest {
    private lateinit var subject: FakeImaginaryNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = FakeImaginaryNetworkDataSource(ioDispatcher = testDispatcher)
    }

    @Test
    fun testDeserializationOfTopics() = runTest(testDispatcher) {
        assertEquals(
            NetworkTopic(
                id = "bo8jQKTaE0Y",
                slug = "wallpapers",
                title = "Wallpapers",
                description = "From epic drone shots to inspiring moments in nature — submit your best desktop and mobile backgrounds.\r\n\r\n",
                totalPhotos = 11814,
                coverPhoto = CoverPhoto(
                    urls = ImageUrls(
                        regular = "https://plus.unsplash.com/premium_photo-1674657644569-787aa58cc804?ixlib=rb-4.0.3\u0026w=1080\u0026fit=max\u0026q=80\u0026fm=jpg\u0026crop=entropy\u0026cs=tinysrgb",
                        small = "https://plus.unsplash.com/premium_photo-1674657644569-787aa58cc804?ixlib=rb-4.0.3\u0026w=400\u0026fit=max\u0026q=80\u0026fm=jpg\u0026crop=entropy\u0026cs=tinysrgb",
                        thumb = "https://plus.unsplash.com/premium_photo-1674657644569-787aa58cc804?ixlib=rb-4.0.3\u0026w=200\u0026fit=max\u0026q=80\u0026fm=jpg\u0026crop=entropy\u0026cs=tinysrgb",
                    )
                ),
            ),
            /* ktlint-enable max-line-length */
            subject.fetchTopics(1).first()
        )
    }

    @Test
    fun testDeserializationOfPhotos() = runTest(testDispatcher) {
        assertEquals(
            NetworkPhoto(
                "vk_Z_ya4u14",
                "2023-02-28T03:02:31Z",
                6000,
                4000,
                "#c0c0c0",
                "L11{Tuay4nofoffQWBay4nj[_3WB",
                57,
                null,
                "Half Moon Photography",
                Photographer(
                    "FXzVgA3vX6M",
                    "VD Photography",
                ),
                ImageUrls(
                    small = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=400",
                    regular = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=1080",
                    thumb = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=200",
                ),
                null,
                Links(
                    "https://unsplash.com/photos/vk_Z_ya4u14/download?ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw",
                    null
                ),
            ),
            /* ktlint-enable max-line-length */
            subject.fetchTopicPhotos("", 1).first()
        )
    }

    @Test
    fun testDeserializationOfPhotoWithId() = runTest(testDispatcher) {
        assertEquals(
            NetworkPhoto(
                "Dwu85P9SOIk",
                "2016-05-03T11:00:28-04:00",
                2448,
                3264,
                "#6E633A",
                "LFC\$yHwc8^\$yIAS\$%M%00KxukYIp",
                24,
                1345,
                "A man drinking a coffee.",
                Photographer(
                    "QPxL2MGqfrw",
                    "Joe Example",
                ),
                ImageUrls(
                    small = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=400&fit=max",
                    regular = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=1080&fit=max",
                    thumb = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=200&fit=max"
                ),
                Location("Montreal", "Canada"),
                Links(
                    "https://unsplash.com/photos/Dwu85P9SOIk/download",
                    null
                ),
            ),
            /* ktlint-enable max-line-length */
            subject.fetchPhoto("")
        )
    }

    @Test
    fun testDeserializationOfPhotosSearchedWithQuery() = runTest(testDispatcher) {
        assertEquals(
            NetworkPhoto(
                "vk_Z_ya4u14",
                "2023-02-28T03:02:31Z",
                6000,
                4000,
                "#c0c0c0",
                "L11{Tuay4nofoffQWBay4nj[_3WB",
                57,
                null,
                "Half Moon Photography",
                Photographer(
                    "FXzVgA3vX6M",
                    "VD Photography",
                ),
                ImageUrls(
                    small = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=400",
                    regular = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=1080",
                    thumb = "https://images.unsplash.com/photo-1677552929439-082dabf4e88f?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=200",
                ),
                null,
                Links(
                    "https://unsplash.com/photos/vk_Z_ya4u14/download?ixid=MnwzODg3MTN8MHwxfHRvcGljfHxibzhqUUtUYUUwWXx8fHx8Mnx8MTY3Nzc2OTI3Mw",
                    null
                ),
            ),
            /* ktlint-enable max-line-length */
            subject.searchPhotos("", 1).result.first()
        )
    }

}
