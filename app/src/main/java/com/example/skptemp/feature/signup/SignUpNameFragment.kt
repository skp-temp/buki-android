package com.example.skptemp.feature.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.skptemp.R
import com.example.skptemp.common.ui.InputBoxView
import com.example.skptemp.common.ui.OnInputFocusListener
import com.example.skptemp.common.ui.OnInputRegexListener
import com.example.skptemp.databinding.FragmentSignUpNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNameFragment : Fragment() {

    private var _binding: FragmentSignUpNameBinding? = null
    private val binding get() = _binding!!

    private lateinit var mActivity: SignUpActivity
    private var mLastNameIsCorrect = false
    private var mFirstNameIsCorrect = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpNameBinding.inflate(inflater, container, false)
        mActivity = requireActivity() as SignUpActivity

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mActivity.enabledNextButton(false)

        binding.root.setOnClickListener {
            hideKeyboard()
            binding.lastNameInput.outOfFocus()
            binding.firstNameInput.outOfFocus()
        }

        binding.firstNameInput.addOnListener(binding.firstNameText, FIRST_NAME)
        binding.lastNameInput.addOnListener(binding.lastNameText, LAST_NAME)
    }

    private fun hideKeyboard() {
        val activity = requireActivity()
        activity.currentFocus ?: return

        val inputManager =
            (activity.getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager

        inputManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun InputBoxView.addOnListener(textView: TextView, nameType: Int) {
        // inputbox 입력값 correct/incorrect 변경 리스너 등록
        addOnInputRegexListener(
            NAME_REGEX,
            object : OnInputRegexListener {
                override fun onChangeCorrect(isCorrect: Boolean, isEmpty: Boolean) {
                    if (nameType == FIRST_NAME) mFirstNameIsCorrect = isCorrect
                    else mLastNameIsCorrect = isCorrect

                    updateNextButton()
                    textView.setMessage(isCorrect || isEmpty)
                }
            }
        )

        // inputbox focus 변경 리스너 등록
        addOnInputFocusListener(object : OnInputFocusListener {
            override fun onChangeFocus(hasFocus: Boolean, isEmpty: Boolean) {
                if (!hasFocus || isEmpty) {
                    textView.setMessage(true)
                    return
                }

                if ((nameType == FIRST_NAME && !mFirstNameIsCorrect) ||
                    nameType == LAST_NAME && !mLastNameIsCorrect
                ) {
                    textView.setMessage(false)
                }
            }
        })
    }

    private fun updateNextButton() {
        if (!mFirstNameIsCorrect || !mLastNameIsCorrect) {
            mActivity.enabledNextButton(false)
            return
        }

        mActivity.enabledNextButton(true)
    }

    private fun TextView.setMessage(flag: Boolean) {
        text = if (flag) "" else resources.getString(R.string.name_input_incorrect)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val NAME_REGEX = "[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]+".toRegex()
        private const val FIRST_NAME = 1
        private const val LAST_NAME = 2
    }
}