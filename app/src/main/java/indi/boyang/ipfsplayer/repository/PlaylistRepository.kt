package indi.boyang.ipfsplayer.repository

import indi.boyang.ipfsplayer.api.MyService

class PlaylistRepository{
    val videos = MyService.create().getPlaylist()
}