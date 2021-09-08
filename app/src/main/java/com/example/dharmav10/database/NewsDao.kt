package com.example.dharmav10.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dharmav10.converters.Converters
import java.util.*

@Dao
@TypeConverters(Converters::class)
interface NewsDao : BaseDao<DatabaseNews> {

    @Query("SELECT * FROM databasenews")
    fun getAllNews(): LiveData<List<DatabaseNews>>

    @Query("SELECT * FROM databasenews WHERE category = :categori")
    fun getCategory(categori: String) : LiveData<List<DatabaseNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: DatabaseNews)

    @Query("DELETE FROM databasenews")
    fun deleteAll()

    @Query("DELETE FROM databasenews WHERE category = :categori")
    fun deleteCategory(categori : String)

    @Query("Select * From databasenews Where guid = :guid and pubDate = :date order by pubDate desc")
    suspend fun getByGuidAndDate(guid: String, date: Date): DatabaseNews?

    @Query("SELECT EXISTS(Select * From DatabaseNews Where guid = :guid and pubDate = :date)")
    fun existsByGuidAndDate(guid: String, date: Date) : Boolean

}



//Initialize Room Database
@Database(entities = [DatabaseNews::class], version = 3)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

//Create Database, with Singleton Concept
private lateinit var INSTANCE: NewsDatabase

fun getDatabase(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news"
            ).build()
        }
    }
    return INSTANCE
}
