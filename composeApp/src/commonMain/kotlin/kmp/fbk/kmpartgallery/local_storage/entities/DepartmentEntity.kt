package kmp.fbk.kmpartgallery.local_storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = DepartmentEntity.TABLE_NAME
)
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = Column.LOCAL_ID) override val localId: Long? = null,
    @ColumnInfo(name = Column.DEPARTMENT_ID) val departmentId: Int? = null,
    @ColumnInfo(name = Column.DISPLAY_NAME) val displayName: String? = null,
): IEntity {
    companion object {
        const val TABLE_NAME = "department_entity"

        object Column {
            const val LOCAL_ID = "local_id"
            const val DEPARTMENT_ID = "department_id"
            const val DISPLAY_NAME = "display_name"
        }
    }
}
