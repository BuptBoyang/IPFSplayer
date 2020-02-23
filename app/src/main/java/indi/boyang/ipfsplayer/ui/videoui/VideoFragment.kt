package indi.boyang.ipfsplayer.ui.videoui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import indi.boyang.ipfsplayer.databinding.FragmentVideoBinding
import indi.boyang.ipfsplayer.util.VideoViewModelFactory
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding
    private lateinit var videoViewModel: VideoViewModel
    val args: VideoFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater,container,false)
        videoViewModel = ViewModelProvider(this, VideoViewModelFactory(args.id))
            .get(VideoViewModel::class.java)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        var shareURL:String = ""
        videoViewModel.videoUrl.observe(viewLifecycleOwner, Observer<String>{
            webView.loadUrl(it)
            textView2.text = it
            shareURL = it
        })
        videoViewModel.title.observe(viewLifecycleOwner, Observer<String> {
            textTitle.text = it
        })
        buttonShare.setOnClickListener{
            val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val cd: ClipData = ClipData.newPlainText("SHARE LINK",shareURL)
            textView2.text="Copy shared link"
            clipboard.primaryClip = cd
        }

        postponeEnterTransition()
        startPostponedEnterTransition()
    }

}