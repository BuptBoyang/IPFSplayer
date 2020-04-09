package indi.boyang.ipfsplayer.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import indi.boyang.ipfsplayer.R
import indi.boyang.ipfsplayer.activities.MainActivity
import indi.boyang.ipfsplayer.util.ToolsViewModelFactory
import kotlinx.android.synthetic.main.fragment_tools.*

class ToolsFragment : Fragment() {

    private lateinit var toolsViewModel: ToolsViewModel
    private lateinit var userName:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: MainActivity? = activity as MainActivity?
        userName = activity!!.getUsername()
        toolsViewModel =
            ViewModelProvider(this,ToolsViewModelFactory(userName)).get(ToolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tools, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolsViewModel.address.observe(viewLifecycleOwner, Observer {
            text_address.text = it
        })
        toolsViewModel.balance.observe(viewLifecycleOwner, Observer {
            text_balance.text = it.toString()
        })

    }
}