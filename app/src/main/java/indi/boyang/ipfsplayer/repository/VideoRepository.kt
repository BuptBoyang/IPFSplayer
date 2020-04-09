package indi.boyang.ipfsplayer.repository

import indi.boyang.ipfsplayer.api.MyService

class VideoRepository(id:Long,operator:String) {
    val video = MyService.create().getVideo(id,operator)
}