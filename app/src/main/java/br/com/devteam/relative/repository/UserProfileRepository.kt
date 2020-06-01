package br.com.devteam.relative.repository

import android.content.Context
import br.com.devteam.relative.R
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.interfaces.DTO
import br.com.devteam.relative.repository.dto.UserProfileDTO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class UserProfileRepository(private val context: Context) :
    FirebaseRepository<UserProfileDTO, UserProfile>(
        context,
        context.resources.getString(R.string.user_profile_ref)
    ) {

    override
    fun getJavaClass(): Class<UserProfileDTO> {
        return UserProfileDTO::class.java
    }

}