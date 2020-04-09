package indi.boyang.ipfsplayer.models

data class Video(
    val id:Long,
    val videoURL: String,
    val picURL: String,
    val title: String,
    val uploader: String
)