package com.developance.imaginary.data.remote

import com.developance.imaginary.photos.domain.UserPhoto

interface Downloader {

    fun downloadFile(userPhoto: UserPhoto): Long
}