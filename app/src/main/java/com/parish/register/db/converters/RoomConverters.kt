package com.parish.register.db.converters

import androidx.room.TypeConverter
import com.parish.register.model.Gender

class RoomConverters {

    @TypeConverter
    fun toGender(value: Int) = enumValues<Gender>()[value]

    @TypeConverter
    fun fromGender(value: Gender) = value.ordinal
}