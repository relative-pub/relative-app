package br.com.devteam.relative.viewmodel

import android.app.Application
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.devteam.relative.domain.ApiResponse
import br.com.devteam.relative.domain.Category
import br.com.devteam.relative.domain.UserProfile
import br.com.devteam.relative.interactor.CategoryInteractor
import br.com.devteam.relative.interactor.UserProfileInteractor

data class UserCategory(var category: Category, var checked: Boolean)

class CategoryViewModel(val app: Application) : AndroidViewModel(app) {

    val categoryInteractor = CategoryInteractor(app.applicationContext)

    val userProfileInteractor = UserProfileInteractor(app.applicationContext)

    val categories = MutableLiveData<List<Category>>()

    val userCategories = MutableLiveData<List<UserCategory>>()

    val userProfile: MutableLiveData<UserProfile> = MutableLiveData()


    init {
        getAll()
        getUserProfile()
    }

    fun getAll() {
        categoryInteractor.getAll {
            if (it!!.success) {
                categories.value = it.data
                prepareUserCategories()
            }
        }
    }

    fun discovery(query: String) {
        getAll()
//        interactor.discovery(query) {
//            if (it != null) {
////                callback(it.items!!)
//                result.value = it.items
//            }
//        }
    }

    fun getCategoryCardImageUrl(
        path: String,
        callback: (apiResponse: ApiResponse<Uri>?) -> Unit
    ) {
        categoryInteractor.getCategoryCardImageUrl(path, callback)
    }

    fun getUserProfile() {
        userProfileInteractor.getUserProfile {
            if (it!!.success) {
                userProfile.value = it.data
                prepareUserCategories()
            }
        }
    }

    fun addCategoryToUserProfile(
        currentUserCategory: UserCategory,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        var listCategory = ArrayList<Category>()
        if (userProfile.value!!.categories != null) {
            listCategory.addAll(userProfile.value!!.categories!!)
        }
        userProfile?.value?.categories = listCategory
        listCategory.add(currentUserCategory.category)
        userProfileInteractor.save(userProfile.value) {
            if (it!!.success) {
                userCategories.value?.forEach {
                    if (it.category.id == currentUserCategory.category.id) {
                        it.checked = true
                    }
                }
            }
            callback(it)
        }
    }

    fun removeCategoryFromUserProfile(
        currentUserCategory: UserCategory,
        callback: (apiResponse: ApiResponse<Unit>?) -> Unit
    ) {
        var listCategory = ArrayList<Category>()
        if (userProfile.value!!.categories != null) {
//            listCategory.addAll(userProfile.value!!.categories!!)
            userProfile.value!!.categories!!.forEach{
                if(it.id != currentUserCategory.category.id){
                    listCategory.add(it)
                }
            }
        }
        userProfile?.value?.categories = listCategory
        userProfileInteractor.save(userProfile.value) {
            if (it!!.success) {
                userCategories.value?.forEach {
                    if (it.category.id == currentUserCategory.category.id) {
                        it.checked = false
                    }
                }
            }
            callback(it)
        }
    }

    fun prepareUserCategories() {
        if (userProfile.value != null && categories.value != null) {
            val listUserCategories = ArrayList<UserCategory>()
            categories.value!!.forEach {
                var userCategory = UserCategory(category = it, checked = false)
                userProfile.value!!.categories?.forEach { category ->
                    if (it.id == category.id) {
                        userCategory.checked = true
                    }
                }
                listUserCategories.add(userCategory)
            }
            userCategories.value = listUserCategories
        }
    }
}
