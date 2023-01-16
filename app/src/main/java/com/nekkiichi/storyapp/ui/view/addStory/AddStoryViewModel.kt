package com.nekkiichi.storyapp.ui.view.addStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.storyapp.data.AppPreferences
import com.nekkiichi.storyapp.data.ResponseStatus
import com.nekkiichi.storyapp.data.StoryRepository
import com.nekkiichi.storyapp.data.remote.response.BasicResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val repository: StoryRepository, private val preferences: AppPreferences): ViewModel() {
    private var _status = MutableStateFlow<ResponseStatus<BasicResponse>>(ResponseStatus.init)
    val status = _status.asStateFlow()
    fun uploadImage(file: File, description: String) {
        viewModelScope.launch {
            repository.uploadStory(file,description).collect {
                _status.value = it
            }
        }
    }
}