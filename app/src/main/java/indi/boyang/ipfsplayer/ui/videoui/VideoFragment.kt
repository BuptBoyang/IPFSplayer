package indi.boyang.ipfsplayer.ui.videoui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import indi.boyang.ipfsplayer.R
import indi.boyang.ipfsplayer.activities.MainActivity
import indi.boyang.ipfsplayer.api.MyService
import indi.boyang.ipfsplayer.databinding.FragmentVideoBinding
import indi.boyang.ipfsplayer.util.VideoViewModelFactory
import kotlinx.android.synthetic.main.fragment_video.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var userName:String

    val args: VideoFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: MainActivity? = activity as MainActivity?
        userName = activity!!.getUsername()
        binding = FragmentVideoBinding.inflate(inflater,container,false)
        videoViewModel = ViewModelProvider(this, VideoViewModelFactory(args.id,userName))
            .get(VideoViewModel::class.java)


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        var shareURL:String = ""
        videoViewModel.videoUrl.observe(viewLifecycleOwner,Observer<String>{
            webView.loadUrl(it)
            shareURL = it
        })
        videoViewModel.title.observe(viewLifecycleOwner, Observer<String> {
            //textTitle.text = it
        })
        imageButtonShare.setOnClickListener{
            val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val cd: ClipData = ClipData.newPlainText("SHARE LINK",shareURL)
            clipboard.primaryClip = cd

            val text = "Share link copied"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }
        imageButtonLike.setOnClickListener{

            MyService.create2().like(id,userName
            ).enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    //
                    println("$id   $userName")
                    imageButtonLike.setImageResource(R.drawable.ic_star_black_24dp)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //
                }
            })
        }

        val settings = webView.settings
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        postponeEnterTransition()
        startPostponedEnterTransition()
    }

}