package kmp.fbk.kmpartgallery.networking.response_data_models

import kotlinx.serialization.Serializable

/**
 * A listing of all valid departments, with their department ID and the department display name.
 */
@Serializable
data class DepartmentsResponse(
    val departments: List<DepartmentResponse>,
)

@Serializable
data class DepartmentResponse(
    val departmentId: Int,
    val displayName: String,
)