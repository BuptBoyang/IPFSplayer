package indi.boyang.ipfsplayer.repository

import indi.boyang.ipfsplayer.api.MyService

class AccountRepository(username:String) {
    var accountInfo = MyService.create().getAccount(username)
}