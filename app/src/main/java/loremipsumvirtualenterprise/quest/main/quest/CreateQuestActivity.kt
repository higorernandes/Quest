package loremipsumvirtualenterprise.quest.main.quest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.res.ResourcesCompat
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.model.QuestLike
import loremipsumvirtualenterprise.quest.model.QuestResponse
import loremipsumvirtualenterprise.quest.util.FirebaseConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CreateQuestActivity : AppCompatActivity()
{

    // Constants
    private val TAG = "CreateQuestActivity"

    //UI elements
    private var questTitleEditText: EditText? = null
    private var questDescriptionEditText: EditText? = null
    private var createQuestButton: Button? = null

    //Firebase references
    private var firebaseDatabaseReference: DatabaseReference? = null

    // Constructor
    companion object {
        fun getActivityIntent(context: Context) : Intent {
            return Intent(context, CreateQuestActivity::class.java)
        }
    }

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quest)
        initializeVariables()
        bindUIElements()
        configureUIElements()
        configureListeners()
    }

    // Initialization
    private fun initializeVariables() {
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference
    }

    // Binding
    private fun bindUIElements() {
        // EditTexts
        questTitleEditText = findViewById<EditText>(R.id.createQuestTitleEditText) as EditText
        questDescriptionEditText = findViewById<EditText>(R.id.createQuestDescriptionEditText) as EditText
        // Buttons
        createQuestButton = findViewById<Button>(R.id.createQuestButton) as Button
        // Other
    }

    // UI Configuration
    private fun configureUIElements() {
        configureEditTexts()
        configureToolbar()
    }

    private fun configureEditTexts() {
        questTitleEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
        questDescriptionEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
    }

    private fun configureToolbar() {
        mainToolbarTitleTextView.text = resources.getString(R.string.create_new_quest)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    // Listeners
    private fun configureListeners() {
        createQuestButton!!.setOnClickListener( { createQuestAndPostToFirebase() } )
    }

    // Validations
    private fun areFieldsValid(): Boolean {
        val isValidTitle = isValidTitle(questTitleEditText?.text.toString())
        val isValidDescription = isValidDescription(questDescriptionEditText?.text.toString())
        return isValidTitle && isValidDescription
    }


    private fun isValidTitle(title: String): Boolean {
        if (TextUtils.isEmpty(title)) {
            questTitleEditText?.error = "Título inválido."
            return false
        }
        questTitleEditText?.error = null
        return true
    }

    private fun isValidDescription(description: String): Boolean {
        if (TextUtils.isEmpty(description)) {
            questDescriptionEditText?.error = "Descrição inválida."
            return false
        }
        questDescriptionEditText?.error = null
        return true
    }

    // Helpers
    @SuppressLint("NewApi")
    private fun currentDateTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        return formatted
    }

    // Actions
    @SuppressLint("NewApi")
    private fun createQuestAndPostToFirebase() {

        if (areFieldsValid()) {

            // Push to firebase in order to get the unique id
            val newFirebaseQuestItem = firebaseDatabaseReference!!.child(FirebaseConstants.FIREBASE_QUESTS_NODE).push()

            // Create and configure Quest object
            val quest = Quest.create()
            quest.id = newFirebaseQuestItem.key
            quest.title = questTitleEditText?.text.toString()
            quest.description = questDescriptionEditText?.text.toString()
            quest.publishedAt = currentDateTime()
            quest.publisherUID = FirebaseAuth.getInstance().currentUser!!.uid

            // Set the value to the newFirebaseQuestItem
            newFirebaseQuestItem.setValue(quest)

            // Dismiss activity and show success dialog
            finish()
            Toast.makeText(this, "New quest saved!", Toast.LENGTH_SHORT).show()
        }
    }

}
