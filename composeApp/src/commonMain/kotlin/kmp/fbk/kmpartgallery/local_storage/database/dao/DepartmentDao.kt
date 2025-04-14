package kmp.fbk.kmpartgallery.local_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kmp.fbk.kmpartgallery.local_storage.database.entities.DepartmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(departmentEntity: DepartmentEntity)

    @Query("SELECT * FROM ${DepartmentEntity.TABLE_NAME}")
    suspend fun getAllDepartments(): List<DepartmentEntity>

    @Query("SELECT * FROM ${DepartmentEntity.TABLE_NAME}")
    fun getAllDepartmentsFlow(): Flow<List<DepartmentEntity>>

}