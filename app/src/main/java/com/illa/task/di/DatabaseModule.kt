package com.illa.task.di

import android.app.Application
import androidx.room.Room
import com.illa.task.data.db.MovieDB
import com.illa.task.data.repository.DatabaseRepositoryImpl
import com.illa.task.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): MovieDB {
        return Room.databaseBuilder(
            app,
            MovieDB::class.java,
            MovieDB.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideRepository(db: MovieDB): DatabaseRepository {
        return DatabaseRepositoryImpl(db)
    }
}