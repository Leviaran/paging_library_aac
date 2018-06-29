package ran.singletondev.testandroid.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ran.singletondev.testandroid.R
import kotlinx.android.synthetic.main.activity_login.*
import ran.singletondev.testandroid.model.Api
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var api : Api

    lateinit var disposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_submit.setOnClickListener {
            doLogin(ed_username.text.toString(),ed_password.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun doLogin(username : String, password : String){
        disposable = api.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it){
                        finish()
                    } else {
                        AlertDialog.Builder(this)
                                .setMessage("invalid Credential")
                                .show()
                    }
                })
    }
}
