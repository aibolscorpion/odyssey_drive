package kz.divtech.odyssey.drive.data.local

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.divtech.odyssey.drive.common.Constants.LOADING
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    suspend fun saveData(key: Preferences.Key<String>, name: String){
        dataStore.edit { preferences ->
            preferences[key] = name
        }
    }

    fun getData(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data
            .map{ preferences ->
                preferences[key] ?: ""
            }
    }

    @Composable
    fun getTokenValue() = getData(TOKEN_KEY).collectAsState(LOADING).value

}