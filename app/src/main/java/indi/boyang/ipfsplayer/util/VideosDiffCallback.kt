package indi.boyang.ipfsplayer.util

import androidx.recyclerview.widget.DiffUtil
import indi.boyang.ipfsplayer.models.Video

class VideosDiffCallback : DiffUtil.ItemCallback<Video>(){
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
        oldItem == newItem

}