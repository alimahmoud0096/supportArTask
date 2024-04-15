package com.smart4apps.supportArTask.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.smart4apps.supportArTask.data.model.SourceItem

class Converters {
    @TypeConverter
    // Function to convert a model object to a JSON string
    fun SourceItemToJsonString(model: SourceItem?): String? {
        return Gson().toJson(model)
    }


    @TypeConverter
    // Function to convert a JSON string to a model object
    fun jsonStringToSourceItem(jsonString: String?): SourceItem? {
        return Gson().fromJson(jsonString, SourceItem::class.java)
    }


}
