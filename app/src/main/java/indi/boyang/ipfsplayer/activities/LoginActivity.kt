package indi.boyang.ipfsplayer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import indi.boyang.ipfsplayer.R
import indi.boyang.ipfsplayer.api.MyService
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var username:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        buttonSubmit.setOnClickListener {
            username = inputUsername.text.toString()
            password = inputPassword.text.toString()

            MyService.create2().login(username,encode(password))
                .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    println(response.toString())
                    if(response.code()==200){
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        var bundle = Bundle()
                        bundle.putString("username",username)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                    if(response.code()==400){
                        textLoginResult.text = response.body().toString()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    textLoginResult.text = "Please try again later"
                }
            })
        }

    }

    private fun encode(text: String): String {
        try {
            val byteArray:ByteArray = MessageDigest.getInstance("MD5").digest(text.toByteArray())
            var stringBuffer = StringBuffer()
            for (b in byteArray) {
                val i :Int = b.toInt() and 0xff
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    hexString = "0$hexString"
                }
                stringBuffer.append(hexString)
            }
            return stringBuffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}
