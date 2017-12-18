package loremipsumvirtualenterprise.quest.main.quest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_quest.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.model.QuestLike
import loremipsumvirtualenterprise.quest.model.QuestResponse
import loremipsumvirtualenterprise.quest.util.FirebaseConstants
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CreateQuestActivity : QuestGenericActivity()
{

    // Constants
    private val TAG = "CreateQuestActivity"

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

        setUpToolbar(mainToolbar as Toolbar?, resources.getString(R.string.create_new_quest))
        changeStatusBarColor()

        configureEditTexts()
        configureListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    // UI Configuration
    private fun configureEditTexts() {
        createQuestTitleEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
        createQuestDescriptionEditText?.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
    }

    // Listeners
    private fun configureListeners() {
        createQuestButton?.setOnClickListener{ createQuestAndPostToFirebase() }
    }

    // Validations
    private fun areFieldsValid(): Boolean {
        val isValidTitle = isValidTitle(createQuestTitleEditText?.text.toString())
        val isValidDescription = isValidDescription(createQuestDescriptionEditText?.text.toString())
        return isValidTitle && isValidDescription
    }


    private fun isValidTitle(title: String): Boolean {
        if (TextUtils.isEmpty(title)) {
            createQuestTitleEditText?.error = "Título inválido."
            return false
        }
        createQuestTitleEditText?.error = null
        return true
    }

    private fun isValidDescription(description: String): Boolean {
        if (TextUtils.isEmpty(description)) {
            createQuestDescriptionEditText?.error = "Descrição inválida."
            return false
        }
        createQuestDescriptionEditText?.error = null
        return true
    }

    // Helpers
    private fun currentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        val date : Date = Date()
        return dateFormat.format(date)
    }

    // Actions
    @SuppressLint("NewApi")
    private fun createQuestAndPostToFirebase() {

        if (areFieldsValid()) {

            // Push to firebase in order to get the unique id
            val newFirebaseQuestItem = FirebaseDatabaseUtil.questsNode?.push()

            // Create and configure Quest object
            val quest = Quest.create()
            quest.id = newFirebaseQuestItem?.key
            quest.title = createQuestTitleEditText?.text.toString()
            quest.description = createQuestDescriptionEditText?.text.toString()
            quest.publishedAt = currentDateTime()
            quest.publisherUID = FirebaseAuth.getInstance().currentUser!!.uid

            // Set the value to the newFirebaseQuestItem
            newFirebaseQuestItem?.setValue(quest)

            // Dismiss activity and show success dialog
            finish()
            Toast.makeText(this, "Nova quest criada!", Toast.LENGTH_SHORT).show()
        }
    }

}
