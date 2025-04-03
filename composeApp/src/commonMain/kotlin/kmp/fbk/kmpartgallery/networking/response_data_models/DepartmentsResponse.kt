package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentsResponse(
    val departments: List<DepartmentResponse>,
)

@Serializable
data class DepartmentResponse(
    val departmentId: Int,
    val displayName: String,
)