/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Abstract classes cannot be instantiated, but they can be subclassed.
@Database(entities = [SleepNight::class], version = 1 , exportSchema = false)
abstract class SleepDatabase: RoomDatabase() {
    abstract val sleepDatabaseDao: SleepDatabaseDao

    //companion object allows clients to use the DB without instantiate this class
    companion object {
        @Volatile //Makes sure the value os instance is always updated, this value is never cached. Changes made in one thread are visible in all threads
        private var INSTANCE: SleepDatabase? = null //Keeps a reference to the database once we have one

        fun getInstance(context: Context) : SleepDatabase {
            //Multiple threads can ask for a database instance at the same time, leaving us with
            //two instead one. This means only one thread can enter this code at the time, which means
            //that only one database is initiated
            synchronized(this) {
                var instance = INSTANCE //kotlin smart cast is only available to local variables and makes this to return only one instance

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database")
                            .fallbackToDestructiveMigration()
                            //if we change the schema we need a way to convert the table in a new schema. This will only rebuild the database so we won't learn data migration.
                           //https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                            .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}