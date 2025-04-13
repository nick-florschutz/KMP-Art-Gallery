package kmp.fbk.kmpartgallery.local_storage.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    require(
        value = context is Context,
        lazyMessage = { "context must be an instance of Context" }
    )

    return AppPreferences.getDataStore {
        context.filesDir
            .resolve(dataStoreFileName)
            .absolutePath
    }
}