package com.example.pressurediary.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pressurediary.R
import com.example.pressurediary.adapter.PressAdapter
import com.example.pressurediary.databinding.HomeFragmentBinding
import com.example.pressurediary.model.PressDat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var firestore: FirebaseFirestore
    private var adapter: PressAdapter? = null
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result -> this.onSignInResult(result) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = Firebase.firestore
        val pressRef = firestore.collection("pressData")
        binding.addButton.setOnClickListener {
            pressRef.add(
                PressDat("30.11", "120", "80", "75")
            )
            pressRef.add(PressDat("30.11", "130", "90", "80"))
            pressRef.add(PressDat("29.11", "130", "90", "80"))

        }
        val query =
            firestore.collection("pressData")
                .orderBy("date", Query.Direction.DESCENDING)
        query.let {
            adapter = PressAdapter(it)
            binding.recyclerView.adapter = adapter
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        viewModel.isSigningIn = false

        if (result.resultCode != Activity.RESULT_OK) {
            if (response == null) {
                requireActivity().finish()
            } else if (response.error != null && response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                showSignInErrorDialog(R.string.message_no_network)
            } else {
                showSignInErrorDialog(R.string.message_unknown)
            }
        }
    }

    private fun showSignInErrorDialog(@StringRes message: Int) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_sign_in_error)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.option_retry) { _, _ -> startSignIn() }
            .setNegativeButton(R.string.option_exit) { _, _ -> requireActivity().finish() }.create()

        dialog.show()
    }

    private fun startSignIn() {
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(intent)
        viewModel.isSigningIn = true
    }

    override fun onStart() {
        super.onStart()

        if (shouldStartSignIn()) {
            startSignIn()
            return
        }
        adapter?.startListening()
    }

    private fun shouldStartSignIn(): Boolean {
        return !viewModel.isSigningIn && Firebase.auth.currentUser == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}