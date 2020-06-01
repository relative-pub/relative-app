package br.com.devteam.relative.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import br.com.devteam.relative.R
import br.com.devteam.relative.databinding.UserProfileFragmentBinding
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.enums.ProfileVisibilityEnum
import br.com.devteam.relative.interfaces.LoadingActivity
import br.com.devteam.relative.view.activity.MainActivity
import br.com.devteam.relative.view.activity.StartActivity
import br.com.devteam.relative.viewmodel.UserProfileViewModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.user_profile_fragment.*

class UserProfileFragment : Fragment() {

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    private lateinit var binding: UserProfileFragmentBinding
    var laodingActivity: LoadingActivity? = null


    private val viewModel: UserProfileViewModel by lazy {
        ViewModelProvider(this).get(UserProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UserProfileFragmentBinding.inflate(inflater, container, false)
        binding.fragment = this@UserProfileFragment
        binding.publicProfile = ProfileVisibilityEnum.PUBLIC

        binding.lifecycleOwner = this
        laodingActivity = activity as LoadingActivity
        loadProfileImage()

        viewModel.userProfile.observe(requireActivity(), Observer {
            binding.userProfile = it
        })

        return binding.root
    }

    fun loadProfileImage() {
        viewModel.userProfileImageURL.observe(requireActivity(), Observer { uri ->
            val view = binding.root.findViewById<CircleImageView>(R.id.iv_profile)
            Picasso.get().load(uri).into(view);
        })
    }

    fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            requireActivity().applicationContext.resources.getInteger(R.integer.IMAGE_PICK_CODE)
        )
    }

    fun checkPermission(view: View) {
        Log.println(Log.INFO, "click", "checkPermission")
        val listPermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions( //Method of Fragment
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requireActivity()
                    .applicationContext.resources.getInteger(R.integer.EXTERNAL_STORAGE_PERMISSION)
            );
        } else {
            pickImageFromGallery();
        }
    }

    fun uploadImage() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == requireActivity()
                .applicationContext.resources.getInteger(R.integer.EXTERNAL_STORAGE_PERMISSION)
        ) {
            pickImageFromGallery()
        } else {
            Toast.makeText(
                requireActivity()
                    .applicationContext, "Permission denied", Toast.LENGTH_SHORT
            ).show()
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == requireActivity()
                .applicationContext.resources.getInteger(R.integer.ACTIVITY_RESULT_OK) &&
            requestCode == requireActivity()
                .applicationContext.resources.getInteger(R.integer.IMAGE_PICK_CODE)
        ) {
            iv_profile.setImageURI(data?.data)
            if (data != null)
                if (data.data != null)
                    viewModel.uploadUserProfileImage(data.data!!) {
                        if (it!!.success) {
                            Toast.makeText(
                                requireActivity()
                                    .applicationContext,
                                "Imagem de perfil salva.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireActivity().applicationContext,
                                it.userMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    }

    fun saveProfile(view: View) {
        laodingActivity?.showLoading()
        viewModel.save(getFormValue()) {
            laodingActivity?.hideLoading()
            if (it!!.success){
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Dados atualizados.",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                Toast.makeText(
                    requireActivity().applicationContext,
                    it.userMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        Log.println(Log.INFO, "click", "saveProfile")
    }

    fun getFormValue(): UserProfile {
        var userProfile = viewModel.userProfile.value
        userProfile?.displayName = et_dysplay_name.text.toString()
        userProfile?.bio = et_bio.text.toString()
        userProfile?.visibilityEnum =
            if (sw_visibility.isChecked) ProfileVisibilityEnum.PUBLIC else ProfileVisibilityEnum.PRIVATE

        return userProfile!!
    }

}
