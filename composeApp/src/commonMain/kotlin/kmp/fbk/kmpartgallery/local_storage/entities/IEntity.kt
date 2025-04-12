package kmp.fbk.kmpartgallery.local_storage.entities

interface IEntity {
    val localId: Long?

    companion object {
        object Column {
            const val LOCAL_ID = "local_id"
        }
    }
}