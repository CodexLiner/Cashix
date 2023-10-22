package com.cashix.kotlin.UI.more_button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashix.database.DatabaseProvider
import com.cashix.databinding.FragmentUserProfileBinding
import com.cashix.utils.SnakeBar
import com.cashix.utils.common
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    val moreViewModel: MoreViewModel by viewModels()
    lateinit var databaseProvider: DatabaseProvider
    lateinit var binding: FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener { common.back(requireActivity()) }
        binding.editProfile.setOnClickListener {
            binding.editProfile.visibility = View.GONE
            binding.saveProfile.visibility = View.VISIBLE
            changeState()
        }
        binding.saveProfile.setOnClickListener {
            binding.editProfile.visibility = View.VISIBLE
            binding.saveProfile.visibility = View.GONE
            editProfile()
            changeState()
        }
        setProfileData()
    }

    private fun editProfile() {
        moreViewModel.updateProfile(
            binding.profilName.text.toString(),
            binding.userEmailEdit.text.toString(),
            binding.pinCode.text.toString()
        )
        moreViewModel.userUpdateResult.observe(viewLifecycleOwner) {
            moreViewModel.saveUserInLocalDatabase(it.user, "")
            SnakeBar(requireActivity()).showSnackbar("Profile Updated Successfully")
        }

    }

    private fun changeState() {
        if (binding.profilName.isEnabled) {
            binding.profilName.requestFocus()
        }
        binding.profilName.isEnabled = !binding.profilName.isEnabled
//        binding.userMobileEdit.isEnabled = !binding.userMobileEdit.isEnabled
        binding.userEmailEdit.isEnabled = !binding.userEmailEdit.isEnabled
        binding.pinCode.isEnabled = !binding.pinCode.isEnabled
    }

    private fun setProfileData() {
        databaseProvider = DatabaseProvider(requireContext())
        binding.profilName.setText(databaseProvider.getUserDB().getUser(1).name.toString())
        binding.userMobileEdit.setText(databaseProvider.getUserDB().getUser(1).mobile.toString())
        binding.userEmailEdit.setText(databaseProvider.getUserDB().getUser(1).email.toString())
        binding.pinCode.setText(databaseProvider.getUserDB().getUser(1).pincode.toString())
    }
}