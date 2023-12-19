package com.example.skptemp.feature.signup

import androidx.lifecycle.ViewModel
import com.example.skptemp.feature.signup.data.Terms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    val termsList: MutableList<Terms>
) : ViewModel() {

    private val termsSize = termsList.size

    private var _termsAgreeCount = MutableStateFlow(termsList.count { it.isChecked.value })
    val termsAgreeCount = _termsAgreeCount.asStateFlow()

    private val _requiredTermsAgreeCount = MutableStateFlow(termsList.count { it.isRequired && it.isChecked.value} )
    val requiredTermsAgreeCount = _requiredTermsAgreeCount.asStateFlow()

    fun agreeTerms(position: Int) {
        if (termsList[position].isChecked.value) _disagreeTerms(position)
        else _agreeTerms(position)
    }

    fun agreeAllTerms() {
        if (termsAgreeCount.value == termsSize) {
            for (position in 0 until termsSize) _disagreeTerms(position)
            return
        }

        for (position in 0 until termsSize) {
            if (!termsList[position].isChecked.value) _agreeTerms(position)
        }
    }

    private fun _agreeTerms(position: Int) {
        termsList[position].isChecked.update { true }
        if (termsList[position].isRequired) _requiredTermsAgreeCount.update { it + 1 }
        _termsAgreeCount.update { it + 1 }
    }

    private fun _disagreeTerms(position: Int) {
        termsList[position].isChecked.update { false }
        if (termsList[position].isRequired) _requiredTermsAgreeCount.update { it - 1 }
        _termsAgreeCount.update { it - 1 }
    }
}