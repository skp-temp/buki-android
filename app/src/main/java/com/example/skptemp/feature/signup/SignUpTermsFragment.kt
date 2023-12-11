package com.example.skptemp.feature.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skptemp.R
import com.example.skptemp.databinding.FragmentSignUpTermsBinding
import com.example.skptemp.feature.signup.CheckCircleColorPicker.setChecked
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpTermsFragment : Fragment() {

    private var _binding: FragmentSignUpTermsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mContext: Context
    private lateinit var mActivity: SignUpActivity

    private val mTermsViewModel: TermsViewModel by viewModels()
    private val mTermsSize by lazy { mTermsViewModel.termsList.size }
    private val mRequiredTermsSize by lazy { mTermsViewModel.termsList.count { it.isRequired } }
    private val mTermsRecyclerViewAdapter by lazy { TermsRecyclerViewAdapter(mContext, viewLifecycleOwner, mTermsViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpTermsBinding.inflate(inflater, container, false)
        mContext = requireContext()
        mActivity = requireActivity() as SignUpActivity

        collectTerms()
        collectRequiredTerms()
        return binding.root
    }

    private fun collectTerms() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // 모든 약관을 동의했을 경우 all agree 버튼 checked
            mTermsViewModel.termsAgreeCount.collectLatest { count ->
                Log.d(TAG, "Terms agree count $count")
                binding.allAgreeView.checkCircle.setChecked(mContext, count == mTermsSize)
            }
        }
    }

    private fun collectRequiredTerms() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // 필수 약관을 모두 동의했을 경우 next button 활성화
            mTermsViewModel.requiredTermsAgreeCount.collectLatest { count ->
                Log.d(TAG, "Required terms agree count $count")
                mActivity.enabledNextButton(count == mRequiredTermsSize)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mActivity.enabledNextButton(mTermsViewModel.requiredTermsAgreeCount.value == mRequiredTermsSize)

        setTermsRecyclerView()
        setAllAgreeTermsView()
    }

    private fun setAllAgreeTermsView() = with(binding.allAgreeView) {
        termsTitle.text = resources.getString(R.string.terms_all_agreement)
        chevronRight.visibility = View.GONE
        checkCircle.setOnClickListener {
            mTermsViewModel.agreeAllTerms()
        }
    }

    private fun setTermsRecyclerView() = with(binding.termsList) {
        layoutManager = LinearLayoutManager(mContext)
        adapter = mTermsRecyclerViewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = SignUpTermsFragment::class.simpleName
    }
}