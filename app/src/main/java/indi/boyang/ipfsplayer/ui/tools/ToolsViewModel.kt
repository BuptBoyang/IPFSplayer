package indi.boyang.ipfsplayer.ui.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import indi.boyang.ipfsplayer.models.AccountInfo
import indi.boyang.ipfsplayer.repository.AccountRepository

class ToolsViewModel(username:String) : ViewModel() {
    val repo = AccountRepository(username)
    val address: LiveData<String> = Transformations.map(repo.accountInfo){ accountInfo -> accountInfo.address }
    val balance: LiveData<Int> = Transformations.map(repo.accountInfo){ accountInfo -> accountInfo.balance }
}