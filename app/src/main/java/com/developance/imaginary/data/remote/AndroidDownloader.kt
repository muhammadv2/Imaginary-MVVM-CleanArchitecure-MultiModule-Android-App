package com.developance.imaginary.data.remote

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.developance.imaginary.R
import com.developance.imaginary.data.di.Const
import com.developance.imaginary.photos.domain.UserPhoto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidDownloader @Inject constructor(@ApplicationContext private val context: Context) :
    Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(userPhoto: UserPhoto): Long {
        val request: DownloadManager.Request =
            DownloadManager.Request(userPhoto.links.download.toUri())
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
                .addRequestHeader("Authorization", " Client-ID ${Const.ACCESS_KEY}")
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "${userPhoto.id}.jpeg"
                )
                .setTitle(context.getString(R.string.app_name)) // Title of the Download Notification
                .setDescription("Downloading") // Description of the Download Notification
                .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true) // Set if download is allowed on roaming network
        return downloadManager.enqueue(request)
    }

}