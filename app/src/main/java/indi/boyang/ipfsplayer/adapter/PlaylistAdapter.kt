package indi.boyang.ipfsplayer.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import indi.boyang.ipfsplayer.databinding.VideoCardBinding
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.util.VideosDiffCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.video_card.*

class PlaylistAdapter:

    ListAdapter<Video, PlaylistAdapter.ViewHolder>(VideosDiffCallback()) {
    lateinit var onItemClickListener: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VideoCardBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: VideoCardBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {
        override val containerView: View = binding.videoCard

        init {
            videoCard.tag = this
            videoCard.setOnClickListener(onItemClickListener)
        }

        fun bind(video: Video) {
            binding.video = video
            binding.executePendingBindings()
        }
    }
}


