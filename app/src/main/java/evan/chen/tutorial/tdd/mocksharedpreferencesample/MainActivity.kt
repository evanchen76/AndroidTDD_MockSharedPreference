package evan.chen.tutorial.tdd.mocksharedpreferencesample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.support.v7.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        send.setOnClickListener {

            val loginId = loginId.text.toString()
            val pwd = password.text.toString()

            val isLoginIdOK = RegisterVerify().isLoginIdVerify(loginId)

            var isPwdOK = false
            //密碼至少8碼，第1碼為英文，並包含1碼數字
            if (pwd.length >= 8) {
                if (pwd.toUpperCase().first() in 'A'..'Z') {
                    if (pwd.findAnyOf((0..9).map { it.toString() }) != null) {
                        isPwdOK = true
                    }
                }
            }

            val builder = AlertDialog.Builder(this)
            if (!isLoginIdOK) {
                // Register fail
                builder.setMessage("loginId at least 8 words and first letter should be alphabetized.")
                    .setTitle("Error")
                builder.show()

            } else if (!isPwdOK) {
                builder.setMessage("Password at least 8 words and first letter should be alphabetized.")
                    .setTitle("Error")
                builder.show()
            } else {
                //註冊成功，儲存Id
                Repository(this).saveUserId(loginId)

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("ID", loginId)

                startActivity(intent)
            }
        }
    }
}
