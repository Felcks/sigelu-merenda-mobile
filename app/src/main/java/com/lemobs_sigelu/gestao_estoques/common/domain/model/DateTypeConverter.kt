package com.lemobs_sigelu.gestao_estoques.common.domain.model

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */

class DateTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}