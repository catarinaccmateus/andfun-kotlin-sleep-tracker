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

import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface SleepDatabaseDao {
    /**
     *  All the functions declared inside the SleepDatabaseDao interface should be declared as suspend.
     *  Suspend functions can be used inside coroutines just like regular functions, but their additional
     *  feature is that they can, in turn, use other suspend functions to suspend execution of a coroutines*/

     @Insert
     fun insert(sleepNight: SleepNight)

     @Update
     fun update(sleepNight: SleepNight)

     @Query("SELECT * from sleep_time_quality_table WHERE nightId = :key")
     fun get(key: Long): SleepNight

     @Query("DELETE FROM sleep_time_quality_table")
      fun clear()

     @Query("SELECT * from sleep_time_quality_table ORDER BY nightId DESC")
     fun getAllNights(): LiveData<List<SleepNight>>

     @Query("SELECT * FROM SLEEP_TIME_QUALITY_TABLE ORDER BY nightId DESC limit 1")
     fun getTonight(): SleepNight? //It is nullable because when we begin the table there are no contents
}
