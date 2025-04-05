package kmp.fbk.kmpartgallery.local_storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kmp.fbk.kmpartgallery.local_storage.entities.DepartmentEntity

@Dao
interface DepartmentDao {

    @Insert
    suspend fun insert(departmentEntity: DepartmentEntity)

    @Query("SELECT * FROM ${DepartmentEntity.TABLE_NAME}")
    suspend fun getAllDepartments(): List<DepartmentEntity>


}