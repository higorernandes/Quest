package loremipsumvirtualenterprise.quest.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.account.OnboardingActivity
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.main.MainActivity
import loremipsumvirtualenterprise.quest.util.SharedPreferencesHelper

class ChangePasswordActivity : QuestGenericActivity()
{

    // Constants
    val TAG = "ChangePasswordActivity"

    // Properties
    private var mHasErrors: Boolean = false

    // Lifecycle
    companion object {
        fun getActivityIntent(context: Context) : Intent {
            return Intent(context, ChangePasswordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setUpToolbar(changePasswordToolbar as Toolbar, resources.getString(R.string.settings_change_password))
        changeStatusBarColor()
        setFonts()
        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    // Configuration
    private fun initViews() {
        changePasswordPasswordEditText.setOnFocusChangeListener { _, hasFocus -> validatePassword(changePasswordPasswordEditText, changePasswordPasswordTextInputLayout, hasFocus) }
        changePasswordConfirmEditText.setOnFocusChangeListener { _, hasFocus -> validatePassword(changePasswordConfirmEditText, changePasswordConfirmTextInputLayout, hasFocus) }

        changePasswordActionButton.setOnClickListener {
            if (!mHasErrors) {
                changePasswordActionButton.isEnabled = false

                // You can get the values from the fields like below:
                val currentUser = FirebaseAuth.getInstance().currentUser!!
                val email = currentUser.email!!
                val currentPassword = changePasswordCurrentEditText.text.toString()
                val newPassword = changePasswordPasswordEditText.text.toString()
                val newPasswordConfirmation = changePasswordConfirmEditText.text.toString()

                if (!mHasErrors) {
                    if (changePasswordPasswordEditText.text.toString() == changePasswordConfirmEditText.text.toString()) {

                        // verify credential and change password
                        val credential = EmailAuthProvider.getCredential(email, currentPassword)
                        currentUser.reauthenticate(credential).addOnCompleteListener { task: Task<Void> ->
                            if (task.isSuccessful) {
                                currentUser.updatePassword(newPassword).addOnCompleteListener { task: Task<Void> ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this@ChangePasswordActivity, "Senha atualizada com sucesso.", Toast.LENGTH_SHORT).show()
                                        finish()
                                    } else {
                                        Toast.makeText(this@ChangePasswordActivity, "Houve um erro ao authenticar o usuário.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this@ChangePasswordActivity, "Houve um erro ao atualizar a senha.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        changePasswordPasswordEditText.error = null
                        changePasswordConfirmEditText.error = null
                    } else {
                        changePasswordActionButton.isEnabled = true
                        changePasswordPasswordEditText.error = resources.getString(R.string.change_password_non_matching_error) //setFontError(resources.getString(R.string.change_password_non_matching_error))
                        changePasswordConfirmEditText.error = resources.getString(R.string.change_password_non_matching_error) //setFontError(resources.getString(R.string.change_password_non_matching_error))
                    }
                }
            }
        }
    }

    private fun setFonts() {
        changePasswordCurrentTextInputLayout.setTypeface(QuestGenericActivity.sAvenirNextRegular)
        changePasswordPasswordTextInputLayout.setTypeface(QuestGenericActivity.sAvenirNextRegular)
        changePasswordConfirmTextInputLayout.setTypeface(QuestGenericActivity.sAvenirNextRegular)

        changePasswordCurrentEditText.typeface = QuestGenericActivity.sAvenirNextRegular
        changePasswordPasswordEditText.typeface = QuestGenericActivity.sAvenirNextRegular
        changePasswordConfirmEditText.typeface = QuestGenericActivity.sAvenirNextRegular
    }

    // Validation
    private fun validatePassword(editText: EditText, inputLayout: TextInputLayout, hasFocus: Boolean) : Boolean {
        if (!hasFocus) {
            if (editText.text.length < 6) {
                mHasErrors = true
                inputLayout.error = resources.getString(R.string.change_password_min_chars_error) //setFontError(resources.getString(R.string.change_password_min_chars_error))
            } else {
                mHasErrors = false
                inputLayout.error = null
            }
        }
        return mHasErrors
    }
}
