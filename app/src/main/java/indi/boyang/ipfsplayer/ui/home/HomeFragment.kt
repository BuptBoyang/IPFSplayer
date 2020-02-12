package indi.boyang.ipfsplayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import indi.boyang.ipfsplayer.R
import indi.boyang.ipfsplayer.adapter.PlaylistAdapter
import indi.boyang.ipfsplayer.models.Video
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = PlaylistAdapter()
            }
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoViewModel = ViewModelProvider(this)
            .get(HomeViewModel::class.java)
        val adapter = video_list.adapter!! as PlaylistAdapter
        videoViewModel.videos.observe(viewLifecycleOwner, Observer<List<Video>> {
            adapter.submitList(it)
            adapter.onItemClickListener = View.OnClickListener { v ->

                val viewHolder = v.tag as RecyclerView.ViewHolder
                val position = viewHolder.adapterPosition
                val id= it[position].id
                val action = HomeFragmentDirections.actionHomeToVideo(id)
                view.findNavController().navigate(action)

            }
        })
    }
}