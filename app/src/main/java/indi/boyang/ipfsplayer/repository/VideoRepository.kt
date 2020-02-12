package indi.boyang.ipfsplayer.repository

import indi.boyang.ipfsplayer.api.MyService

class VideoRepository(id:Long) {
    val video = MyService.create().getVideo(id)
}