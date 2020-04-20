package br.com.devteam.relative.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.interactor.UserProfileInteractor

class UserProfileViewModel (val app: Application) : AndroidViewModel(app) {

    private val interactor = UserProfileInteractor(app.applicationContext)

    val userProfile: MutableLiveData<UserProfile> = MutableLiveData()


    fun save( callback: (apiResponse: ApiResponse?) -> Unit) {
        interactor.save(userProfile.value, callback)
    }
}