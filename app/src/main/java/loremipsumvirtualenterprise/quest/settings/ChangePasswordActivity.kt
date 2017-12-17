package loremipsumvirtualenterprise.quest.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_change_password.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity

class ChangePasswordActivity : QuestGenericActivity()
{

    private var mHasErrors: Boolean = false

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

    private fun initViews() {
        changePasswordPasswordEditText.setOnFocusChangeListener { _, hasFocus -> validatePassword(changePasswordPasswordEditText, changePasswordPasswordTextInputLayout, hasFocus) }
        changePasswordConfirmEditText.setOnFocusChangeListener { _, hasFocus -> validatePassword(changePasswordConfirmEditText, changePasswordConfirmTextInputLayout, hasFocus) }

        changePasswordActionButton.setOnClickListener {
            if (!mHasErrors) {
                changePasswordActionButton.isEnabled = false

                // You can get the values from the fields like below:
                val current = changePasswordCurrentEditText.text.toString()
                val new = changePasswordPasswordEditText.text.toString()
                val confirmation = changePasswordConfirmEditText.text.toString()

                if (!mHasErrors) {
                    if (changePasswordPasswordEditText.text.toString() == changePasswordConfirmEditText.text.toString()) {

                        //TODO: call service to check current password
                        //TODO: call service to change password

                        changePasswordPasswordEditText.error = null
                        changePasswordConfirmEditText.error = null
                    } else {
                        changePasswordActionButton.isEnabled = true
                        changePasswordPasswordEditText.error = setFontError(resources.getString(R.string.change_password_non_matching_error))
                        changePasswordConfirmEditText.error = setFontError(resources.getString(R.string.change_password_non_matching_error))
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

    private fun validatePassword(editText: EditText, inputLayout: TextInputLayout, hasFocus: Boolean) : Boolean {
        if (!hasFocus) {
            if (editText.text.length < 6) {
                mHasErrors = true
                inputLayout.error = setFontError(resources.getString(R.string.change_password_min_chars_error))
            } else {
                mHasErrors = false
                inputLayout.error = null
            }
        }
        return mHasErrors
    }
}
