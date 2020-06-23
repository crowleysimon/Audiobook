package com.crowleysimon.audiobook

import java.io.File

object Playlist {
    var files: List<File>? = null
    var currentFile: File? = null
    var currentTimeStamp: Long = 0L
}