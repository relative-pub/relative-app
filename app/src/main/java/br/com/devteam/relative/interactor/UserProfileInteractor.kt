package br.com.devteam.relative.interactor

import android.content.Context
import br.com.devteam.relative.R
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.repository.UserProfileRepository

class UserProfileInteractor(private val context: Context) {
    val repository = UserProfileRepository(context)


    fun save(userProfile: UserProfile?, callback: (apiResponse: ApiResponse?) -> Unit) {
        if (userProfile != null) {
            repository.save(userProfile, callback)
        }
    }

    fun getUserProfile(callback: (userProfile: UserProfile?) -> Unit){
        repository.getUserProfile(callback)
    }
}