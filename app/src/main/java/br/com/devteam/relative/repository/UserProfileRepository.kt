package br.com.devteam.relative.repository

import android.content.Context
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.repository.dto.UserProfileDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class UserProfileRepository(context: Context){

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getUserDbRef(): DatabaseReference {
        val uid = auth.currentUser!!.uid
        return database.getReference("user_profile/$uid")
    }

    fun save(spotifyProfile: UserProfile, callback: (apiResponse: ApiResponse?) -> Unit) {
        val ref = getUserDbRef()
        ref.setValue(spotifyProfile).addOnSuccessListener {
            callback(ApiResponse())
        }.addOnFailureListener {
            callback(
                ApiResponse(
                    false,
                    userMessage = "Houve um problema ao salvar os dados do usuário."
                )
            )
        }
    }

    fun getUserProfile(callback: (userProfile: UserProfile?) -> Unit) {
        val ref = getUserDbRef()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callback(null)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfileDTO = snapshot.getValue(UserProfileDTO::class.java)
                if (userProfileDTO != null) {
                    callback(userProfileDTO.toDomain())
                } else {
                    callback(null)
                }

            }
        })
    }
}