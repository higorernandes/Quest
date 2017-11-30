package loremipsumvirtualenterprise.quest.account

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_login.*
import loremipsumvirtualenterprise.quest.R

class RegisterActivity : AppCompatActivity() {

    //UI elements
    private var fullNameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var createAccountButton: Button? = null
    private var progressDialog: ProgressBar? = null

    // Lifecycle
    companion object {
        fun getActivityIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, RegisterActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bindUIElements()
        configureUIElements()
        configureListeners()
    }

    // Binding
    private fun bindUIElements() {
        // EditTexts
        fullNameEditText = findViewById<EditText>(R.id.et_full_name) as EditText
        emailEditText = findViewById<EditText>(R.id.et_email) as EditText
        passwordEditText = findViewById<EditText>(R.id.et_password) as EditText
        // Buttons
        createAccountButton = findViewById<Button>(R.id.btn_create_account) as Button
        // Other
        progressDialog = ProgressBar(this)
    }

    // UI Configuration
    private fun configureUIElements() {
        configureEditTexts()
        configureStatusBar()
    }

    private fun configureEditTexts() {
        fullNameEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
        emailEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
        passwordEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
    }

    private fun configureStatusBar() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    // Listeners
    private fun configureListeners() {
        createAccountButton!!.setOnClickListener( { createAccount() } )
    }

   // Actions
    private  fun createAccount() {
       print("coisa")
   }


}
