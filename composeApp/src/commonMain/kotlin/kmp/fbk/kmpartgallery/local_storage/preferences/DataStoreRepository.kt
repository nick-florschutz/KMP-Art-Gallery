package kmp.fbk.kmpartgallery.local_storage.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>,
//    private val userDao: UserDao,
) {

    fun readStringValueFlow(key: String): Flow<String?> {
        return dataStore.data
            .catch {
                Napier.e { "Failed to read String value from datastore. Key: $key" }
                emptyFlow<String>()
            }
            .map { preferences ->
                preferences[stringPreferencesKey(getUserScopedDataStoreKey(key))]
            }
    }

    suspend fun readStringValue(key: String): String? {
        val userScopedKey = getUserScopedDataStoreKey(key)
        return dataStore.data
            .catch {
                Napier.e { "Failed to read String value from datastore. Key: $userScopedKey" }
                emptyFlow<String>()
            }
            .map { preferences ->
                preferences[stringPreferencesKey(userScopedKey)]
            }.first()
    }

    fun readLongValueFlow(key: String): Flow<Long?> {
        return dataStore.data
            .catch {
                Napier.e { "Failed to read String value from datastore. Key: $key" }
                emptyFlow<Long>()
            }
            .map { preferences ->
                preferences[longPreferencesKey(getUserScopedDataStoreKey(key))]
            }
    }

    suspend fun readLongValue(key: String): Long? {
        val userScopedKey = getUserScopedDataStoreKey(key)
        return dataStore.data
            .catch {
                Napier.e { "Failed to read String value from datastore. Key: $userScopedKey" }
                emptyFlow<Long>()
            }
            .map { preferences ->
                preferences[longPreferencesKey(userScopedKey)]
            }.first()
    }

    suspend fun saveStringValue(key: String, value: String): Boolean {
        val userScopedKey = getUserScopedDataStoreKey(key)

        try {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(userScopedKey)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Napier.e { "Failed to save String value to datastore. Key: $userScopedKey\nValue: $value\nError: ${e.message}\n" }
            println(e.message)
            return false
        }
    }

    suspend fun saveBooleanValue(key: String, value: Boolean): Boolean {
        val userScopedKey = getUserScopedDataStoreKey(key)
        try {
            dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Napier.e { "Failed to save Boolean value to datastore. Key: $userScopedKey\nValue: $value\nError: ${e.message}\n" }
            println(e.message)
            return false
        }
    }

    suspend fun saveIntValue(key: String, value: Int): Boolean {
        val userScopedKey = getUserScopedDataStoreKey(key)
        try {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Napier.e { "Failed to save Int value to datastore. Key: $userScopedKey\nValue: $value\nError: ${e.message}\n" }
            println(e.message)
            return false
        }
    }

    suspend fun saveLongValue(key: String, value: Long): Boolean {
        val userScopedKey = getUserScopedDataStoreKey(key)
        try {
            dataStore.edit { preferences ->
                preferences[longPreferencesKey(userScopedKey)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            Napier.e { "Failed to save long value to datastore. Key: $userScopedKey\nValue: $value\nError: ${e.message}\n" }
            return false
        }
    }

    suspend fun clearDatastoreValue(key: String): Boolean {
        val userScopedKey = getUserScopedDataStoreKey(key)
        try {
            dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(userScopedKey))
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Napier.e { "Failed to clear datastore value. Key: $userScopedKey Error: ${e.message}" }
            println(e.message)
            return false
        }
    }

    /**
     * Gets the user scoped datastore key.
     * This is a best effort attempt to get a unique key for the user. We will first try their
     * server ID, but if that is null we will fallback to their local ID.
     */
    private suspend fun getUserScopedDataStoreKey(key: String): String {
//        val userServerId = userDao.getCurrentlySignedInUserServerId()
//        val userLocalId = userDao.getCurrentlySignedInUserLocalId()
//        val formattedServerIdForDatastoreKey = "_${userServerId ?: userLocalId}"
//        return key + formattedServerIdForDatastoreKey
        return key
    }
}