package kmp.fbk.kmpartgallery.local_storage.database.entities

interface IEntity {
    val localId: Long?

    companion object {
        object Column {
            const val LOCAL_ID = "local_id"
        }
    }
}