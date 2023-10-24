package kz.divtech.odyssey.drive.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.divtech.odyssey.drive.data.local.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val dataStoreManager: DataStoreManager): ViewModel() {
    private val _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> get() = _title

    fun setTitle(title: String){
        _title.value = title
    }
}