package indi.boyang.ipfsplayer.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import indi.boyang.ipfsplayer.ui.tools.ToolsViewModel

class ToolsViewModelFactory(private val username:String):
ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ToolsViewModel(username) as T
}