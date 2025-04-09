package kmp.fbk.kmpartgallery.local_storage.mappers

import kmp.fbk.kmpartgallery.local_storage.domain_models.Department
import kmp.fbk.kmpartgallery.local_storage.entities.DepartmentEntity
import kmp.fbk.kmpartgallery.networking.response_data_models.DepartmentResponse

fun DepartmentEntity.toDepartment(): Department {
    return Department(
        localId = localId,
        departmentId = departmentId,
        displayName = displayName,
    )
}

fun Department.toDepartmentEntity(): DepartmentEntity {
    return DepartmentEntity(
        localId = localId,
        departmentId = departmentId,
        displayName = displayName,
    )
}

fun DepartmentResponse.toDepartment(): Department {
    return Department(
        localId = null,
        departmentId = departmentId,
        displayName = displayName,
    )
}

fun DepartmentResponse.toDepartmentEntity(): DepartmentEntity {
    return DepartmentEntity(
        localId = null,
        departmentId = departmentId,
        displayName = displayName,
    )
}

fun List<DepartmentEntity>.toDepartmentsList(): List<Department> {
    return map { it.toDepartment() }
}

fun List<DepartmentResponse>.toDepartmentList(): List<Department> {
    return map { it.toDepartment() }
}

fun List<DepartmentResponse>.toDepartmentEntityList(): List<DepartmentEntity> {
    return map { it.toDepartmentEntity() }
}

fun List<Department>.toDepartmentEntitiesList(): List<DepartmentEntity> {
    return map { it.toDepartmentEntity() }
}