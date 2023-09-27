package kz.divtech.odyssey.drive.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> get() = _title

    fun setTitle(title: String){
        _title.value = title
    }
}