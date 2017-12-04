package loremipsumvirtualenterprise.quest.account

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import loremipsumvirtualenterprise.quest.R

/**
 * Created by bocato on 03/12/17.
 */
class ForgotPasswordActivity : AppCompatActivity() {

    // Constants
    private val TAG = "ForgotPasswordActivity"

    //UI elements
    private var emailEditText: EditText? = null
    private var resetPasswordButton: Button? = null

    // Properties

    //Firebase references
    private var firebaseAuth: FirebaseAuth? = null

    // Lifecycle
    companion object {
        fun getActivityIntent(context: Context): Intent {
            val intent: Intent = Intent(context, ForgotPasswordActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        initializeVariables()
        bindUIElements()
        configureUIElements()
        configureListeners()
    }

    // Initialization
    private fun initializeVariables() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    // Binding
    private fun bindUIElements() {
        // EditTexts
        emailEditText = findViewById<EditText>(R.id.et_reset_password_email) as EditText
        // Buttons
        resetPasswordButton = findViewById<Button>(R.id.btn_reset_password) as Button
        // Other
    }

    // UI Configuration
    private fun configureUIElements() {
        configureEditTexts()
        configureStatusBar()
        configureToolbar()
    }

    private fun configureEditTexts() {
        emailEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
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

    private fun configureToolbar() {
        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun changeStatusBarColor() {
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
        resetPasswordButton!!.setOnClickListener({ resetPassword() })
    }

    // Actions
    private fun resetPassword() {
        val email = emailEditText?.text.toString()
        if (isValidEmail(email)) {

            // TODO: Start Progress View

            // Call FirebaseAuth
            firebaseAuth!!
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        // TODO: Stop Progress View
                        if (task.isSuccessful) {
                            Toast.makeText(this@ForgotPasswordActivity, "Uma nova senha foi enviada para " + email + ".", Toast.LENGTH_SHORT).show()
                            super.onBackPressed()
                        } else {
                            val message = task.exception?.localizedMessage
                            Toast.makeText(this@ForgotPasswordActivity, "Houve um erro ao resetar a senha de " + email + ".\n" + message, Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            emailEditText?.error = "E-Mail inválido."
            return false
        }
        emailEditText?.error = null
        return true
    }
}