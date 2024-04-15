package com.smart4apps.supportArTask.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.utils.Converters

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

}