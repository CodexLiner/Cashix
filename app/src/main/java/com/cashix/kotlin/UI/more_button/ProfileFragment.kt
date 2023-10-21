package com.cashix.kotlin.UI.more_button

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cashix.R
import com.cashix.database.DatabaseProvider
import com.cashix.databinding.FragmentUserProfileBinding
import com.cashix.utils.common
import net.one97.paytm.nativesdk.common.model.RiskExtendedInfoHolder.convert

class ProfileFragment : Fragment() {
    private var EditProfileState: Boolean = false
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
        Log.d("TAG", "DaggerTest editProfile: ")
    }

    private fun changeState() {
        binding.profilName.isEnabled = !binding.profilName.isEnabled
        binding.userMobileEdit.isEnabled = !binding.userMobileEdit.isEnabled
        binding.userEmailEdit.isEnabled = !binding.userEmailEdit.isEnabled
        binding.pinCode.isEnabled = !binding.pinCode.isEnabled
    }

    private fun setProfileData() {
        databaseProvider = DatabaseProvider(requireContext())
        binding.profilName.setText(databaseProvider.getUser().getUser(1).name.toString())
        binding.userMobileEdit.setText(databaseProvider.getUser().getUser(1).mobile.toString())
        binding.userEmailEdit.setText(databaseProvider.getUser().getUser(1).email.toString())
        binding.pinCode.setText(databaseProvider.getUser().getUser(1).pincode.toString())
    }
}