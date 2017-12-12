package loremipsumvirtualenterprise.quest.account

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.text.TextUtils.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.main.MainActivity
import loremipsumvirtualenterprise.quest.model.QuestUser
import loremipsumvirtualenterprise.quest.util.FirebaseConstants
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(){

    // Constants
    private val TAG = "RegisterActivity"

    //UI elements
    private var fullNameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var createAccountButton: Button? = null

    // Properties

    //Firebase references
    private var firebaseAuth: FirebaseAuth? = null

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
        fullNameEditText = findViewById<EditText>(R.id.et_full_name) as EditText
        emailEditText = findViewById<EditText>(R.id.et_email) as EditText
        passwordEditText = findViewById<EditText>(R.id.et_password) as EditText
        // Buttons
        createAccountButton = findViewById<Button>(R.id.btn_create_account) as Button
        // Other
    }

    // UI Configuration
    private fun configureUIElements() {
        configureEditTexts()
        configureStatusBar()
        configureToolbar()
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
        createAccountButton!!.setOnClickListener( { createAccount() } )
    }

    // Actions
    private  fun createAccount() {
        if (areFieldsValid()) {
            // Get Data
            val fullName = fullNameEditText!!.text.toString()
            val email = emailEditText!!.text.toString()
            val password = passwordEditText!!.text.toString()

            // TODO: Start Progress View

            // Call FirebaseAuth
            firebaseAuth!!
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task: Task<AuthResult> ->
                        // TODO: Stop Progress View
                        if (task.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "Usuário criado com sucesso.\nLogando...", Toast.LENGTH_SHORT).show()
                            loginUser(email, password, fullName)
                        } else {
                            val message = task.exception?.getLocalizedMessage()
                            Toast.makeText(this@RegisterActivity, "Houve um erro ao registrar o usuário.\n" + message, Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    // Actions
    @SuppressLint("NewApi")
    private fun createQuestUserAndSaveToFirebase(email: String, name: String, uid: String) {
        // Push to firebase in order to get the unique id
        val newFirebaseQuestUser = FirebaseDatabaseUtil.usersNode?.child(uid)

        // Create and configure QuestUser object
        val questUser = QuestUser.create()
        questUser.email = email
        questUser.name = name
        questUser.uid = uid

        // Set the value to the newFirebaseQuestItem
        newFirebaseQuestUser?.setValue(questUser)
    }


    private fun loginUser(email: String, password: String, fullName: String) {
        // TODO: Start Progress View
        firebaseAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                createQuestUserAndSaveToFirebase(email, fullName, uid!!)
                // TODO: Stop Progress View
                val mainActivityIntent : Intent = MainActivity.getActivityIntent(this)
                startActivity(mainActivityIntent)
            } else {
                // TODO: Go back to login
                super.onBackPressed()
            }
        }
    }

    // Helpers
    private fun currentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        val date : Date = Date()
        return dateFormat.format(date)
    }

    // Validation
    private fun areFieldsValid(): Boolean {
        val isValidName = isValidName(fullNameEditText?.text.toString())
        val isValidEmail = isValidEmail(emailEditText?.text.toString())
        val isValidPassword =  isValidPassword(passwordEditText?.text.toString())
        return isValidName && isValidEmail && isValidPassword
    }


    private fun isValidName(fullName: String): Boolean {
        if (TextUtils.isEmpty(fullName)) {
            fullNameEditText?.error = "Nome inválido."
            return false
        }
        fullNameEditText?.error = null
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            emailEditText?.error = "E-Mail inválido."
            return false
        }
        emailEditText?.error = null
        return true
    }

    private fun isValidPassword(password: String): Boolean {
        if (TextUtils.isEmpty(password)) {
            passwordEditText?.error = "Senha inválida."
            return false
        } else if(password.length < 6) {
            passwordEditText?.error = "A senha precisa ter no mínimo 6 caracteres."
            return false
        }
        passwordEditText?.error = null
        return true
    }


}