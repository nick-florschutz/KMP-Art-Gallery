package kmp.fbk.kmpartgallery.local_storage.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val localId: Long?,
    val departmentId: Int?,
    val displayName: String?,
)
