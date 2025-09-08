package com.example.cinema.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cinema.activity.ChangePasswordActivity
import com.example.cinema.activity.ChatBotGPT
import com.example.cinema.activity.SignInActivity
import com.example.cinema.constant.GlobalFunction.startActivity
import com.example.cinema.databinding.FragmentAccountBinding
import com.example.cinema.prefs.DataStoreManager
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        fragmentAccountBinding.tvEmail.text = DataStoreManager.getUser()?.email
        fragmentAccountBinding.layoutSignOut.setOnClickListener { onClickSignOut() }
        fragmentAccountBinding.layoutChangePassword.setOnClickListener { onClickChangePassword() }
        fragmentAccountBinding.layoutTalkToChatBot.setOnClickListener { onClickTalkToChatBot() }
        return fragmentAccountBinding.root
    }

    private fun onClickTalkToChatBot() {
        startActivity(activity, ChatBotGPT::class.java)
    }

    private fun onClickChangePassword() {
        startActivity(activity, ChangePasswordActivity::class.java)
    }

    private fun onClickSignOut() {
        if (activity === null) {
            return
        }
        FirebaseAuth.getInstance().signOut()
        DataStoreManager.setUser(null)
        startActivity(activity, SignInActivity::class.java)
        requireActivity().finishAffinity()
    }
}