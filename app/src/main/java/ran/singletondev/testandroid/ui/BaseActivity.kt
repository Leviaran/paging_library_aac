package ran.singletondev.testandroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ran.singletondev.testandroid.R
import ran.singletondev.testandroid.model.Api
import javax.inject.Inject

class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var api : Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

}
