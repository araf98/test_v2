package com.v2_assesment.myassesment.Repository

import android.content.Context
import androidx.room.Room
import com.v2_assesment.myassesment.APIBackend.QuestionsAPI
import com.v2_assesment.myassesment.Room.AppDatabase
import com.v2_assesment.myassesment.Room.RecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "questions_database"
        ).build()
    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): RecordDao = database.recordDao()


    @Singleton
    @Provides
    fun provideRepository(dao: RecordDao,api: QuestionsAPI): Repository = RepoImple(dao,api)
}
