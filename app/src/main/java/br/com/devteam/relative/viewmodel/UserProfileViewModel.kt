package br.com.devteam.relative.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.Category
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.interactor.CategoryInteractor
import br.com.devteam.relative.interactor.UserProfileInteractor

class UserProfileViewModel(val app: Application) : AndroidViewModel(app) {

    private val interactor = UserProfileInteractor(app.applicationContext)

    val userProfile: MutableLiveData<UserProfile> = MutableLiveData()
    val userProfileImageURL: MutableLiveData<Uri> = MutableLiveData()

    var changedUserProfileImageURL: MutableLiveData<Boolean> = MutableLiveData()

    // Category
    val categoryInteractor = CategoryInteractor(app.applicationContext)
    val categories = MutableLiveData<List<Category>>()


    init {
        getUserProfile()
        getUserProfileImageUri()
    }

    fun save(userP: UserProfile, callback: (apiResponse: ApiResponse<Unit>?) -> Unit) {
        interactor.save(userP) { saveResponse ->
            if(saveResponse!!.success){
                userProfile.value = userP
            }
//            getUserProfile()
            callback(saveResponse)
        }
    }

    fun uploadUserProfileImage(
        localFilePath: Uri,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        interactor.uploadUserPrpofileImage(localFilePath) {
            if (it!!.success) {
                changedUserProfileImageURL.value = true
                getUserProfileImageUri()
            }
            callback(it)
        }
    }

    fun getUserProfileImageUri() {
        interactor.getUserProfileImageUrl {
            if (it!!.success) {
                userProfileImageURL.value = it.data
            }
        }
    }

    fun getUserProfile() {
        interactor.getUserProfile {
            if (it!!.success) {
                userProfile.value = it.data
            }
        }
    }

    fun addCategoryToUserProfile(userProfile: UserProfile, callback: (apiResponse: ApiResponse<Uri>?) -> Unit){
        var listCategory = ArrayList<Category>()

    }

    fun removeCategoryFromUserProfile(userProfile: UserProfile, callback: (apiResponse: ApiResponse<Uri>?) -> Unit){

    }
}