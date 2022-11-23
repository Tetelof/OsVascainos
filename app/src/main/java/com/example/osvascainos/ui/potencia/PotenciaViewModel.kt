package com.example.osvascainos.ui.potencia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PotenciaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is potencia Fragment"
    }
    val text: LiveData<String> = _text
}