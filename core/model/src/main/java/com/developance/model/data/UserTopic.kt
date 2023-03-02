/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.developance.model.data

data class UserTopic(
    val id: String,
    val title: String,
    val slug: String,
    val description:String,
    val isFavorite: Boolean,
    val totalPhotos: Long,
    val coverPhoto: String?
)

val defaultRandomTopic = UserTopic(
    "1",
    "Random",
    "wallpapers",
    "Random",
    true,
    58,
    ""
)
fun previewUserUserTopics() = listOf(
        UserTopic(
            "1",
            "Architecture",
            "Architecture",
            "Architecture",
            false,
            58,
            "https://images.unsplash.com/photo-1479839672679-a46483c0e7c8"
        ),
        UserTopic(
            "2",
            "Arts & Crafts",
            "Arts & Crafts",
            "Arts & Crafts",
            true,
            121,
            "https://images.unsplash.com/photo-1422246358533-95dcd3d48961"
        ),
        UserTopic(
            "3",
            "Business",
            "Business",
            "Business",
            false,
            78,
            "https://images.unsplash.com/photo-1507679799987-c73779587ccf"
        ),

        UserTopic(
            "4",
            "Culinary",
            "Culinary",
            "Culinary",
            true, 118,
            "https://images.unsplash.com/photo-1551218808-94e220e084d2"
        ),
        UserTopic(
            "5",
            "Design",
            "Design",
            "Design",
            false,
            423,
            "https://images.unsplash.com/photo-1493932484895-752d1471eab5"
        ),
        UserTopic(
            "6",
            "Fashion",
            "Fashion",
            "Fashion",
            true,
            92,
            "https://images.unsplash.com/photo-1517840545241-b491010a8af4"
        ),
        UserTopic(
            "7",
            "Film",
            "Film",
            "Film",
            false,
            165,
            "https://images.unsplash.com/photo-1518676590629-3dcbd9c5a5c9"
        ),
        UserTopic(
            "8",
            "Gaming",
            "Gaming",
            "Gaming",
            false,
            164,
            "https://images.unsplash.com/photo-1528870884180-5649b20f6435"
        ),
        UserTopic(
            "9",
            "Illustration",
            "Illustration",
            "Illustration",
            true,
            326,
            "https://images.unsplash.com/photo-1526312426976-f4d754fa9bd6"
        ),
        UserTopic(
            "10",
            "Lifestyle",
            "Lifestyle",
            "Lifestyle",
            true,
            305,
            "https://images.unsplash.com/photo-1471560090527-d1af5e4e6eb6"
        ),
        UserTopic(
            "11",
            "Music",
            "Music",
            "Music",
            false,
            212,
            "https://images.unsplash.com/photo-1454922915609-78549ad709bb"
        ),
        UserTopic(
            "12",
            "Painting",
            "Painting",
            "Painting",
            true,
            172,
            "https://images.unsplash.com/photo-1461344577544-4e5dc9487184"
        ),
        UserTopic(
            "13",
            "Photography",
            "Photography",
            "Photography",
            false,
            321,
            "https://images.unsplash.com/photo-1542567455-cd733f23fbb1"
        ),
        UserTopic(
            "14",
            "Technology",
            "Technology",
            "Technology",
            true,
            118,
            "https://images.unsplash.com/photo-1535223289827-42f1e9919769"
        )
    )