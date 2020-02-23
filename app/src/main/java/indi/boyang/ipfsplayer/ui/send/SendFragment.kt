package indi.boyang.ipfsplayer.ui.send

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import indi.boyang.ipfsplayer.api.ApiSuccessResponse
import indi.boyang.ipfsplayer.api.MyService
import indi.boyang.ipfsplayer.models.Video
import indi.boyang.ipfsplayer.util.SendViewModelFactory
import kotlinx.android.synthetic.main.fragment_send.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class SendFragment : Fragment() {

    private val PHOTO_REQUEST_CODE = 1
    private val VIDEO_REQUEST_CODE = 2
    private var coverUri: Uri? = null
    private var videoUri: Uri? = null

    private lateinit var sendViewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(indi.boyang.ipfsplayer.R.layout.fragment_send, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonChooseCover.setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK)
            pickPhotoIntent.type = "image/*"
            startActivityForResult(pickPhotoIntent,PHOTO_REQUEST_CODE)
        }

        buttonChooseVideo.setOnClickListener {
            val pickVideoIntent = Intent(Intent.ACTION_PICK)
            pickVideoIntent.type = "video/*"
            startActivityForResult(pickVideoIntent,VIDEO_REQUEST_CODE)
        }

        buttonUpload.setOnClickListener {
            if(coverUri != null && videoUri != null) {
                val titlePart = editTitle.text.toString()
                Log.d("URI", coverUri.toString())
                Log.d("URI", videoUri.toString())
                sendViewModel = ViewModelProvider(this,
                    SendViewModelFactory(titlePart, coverUri as Uri, videoUri as Uri))
                    .get(SendViewModel::class.java)
                sendViewModel.video.observe(viewLifecycleOwner, Observer<Video> {
                    Log.d("TITLE", it.title)
                })
                uploadResultView.text = "finish"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            PHOTO_REQUEST_CODE -> if (data != null) {
                coverUri = getRealPathFromURI(context!!, data.data!!)
                coverFileName.text = coverUri.toString()
            }
            VIDEO_REQUEST_CODE -> if (data != null) {
                videoUri = getRealPathFromURI(context!!, data.data!!)
                videoFileName.text = videoUri.toString()
            }
        }
    }

    private fun getRealPathFromURI(
        context: Context,
        contentUri: Uri
    ): Uri {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()!!
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result: String = cursor.getString(column_index)
        cursor.close()
        return result.toUri()
    }

}