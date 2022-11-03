package com.parish.register.common

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.parish.register.model.FilterType
import com.parish.register.model.ListFilter
import com.parish.register.model.SortingType

class GsonListFilterAdapter : TypeAdapter<ListFilter>() {

    companion object {
        private const val FILTER_TYPE = "FILTER_TYPE"
        private const val PERIOD_FROM = "PERIOD_FROM"
        private const val PERIOD_TO = "PERIOD_TO"
        private const val SORTING_TYPE = "SORTING_TYPE"
    }

    override fun write(writer: JsonWriter, value: ListFilter) {
        writer.beginObject()
        writer.name(FILTER_TYPE)
        writer.value(value.type.id)
        writer.name(PERIOD_FROM)
        writer.value(value.periodFrom)
        writer.name(PERIOD_TO)
        writer.value(value.periodTo)
        writer.name(SORTING_TYPE)
        writer.value(value.sortingType.id)
        writer.endObject()
    }

    override fun read(reader: JsonReader): ListFilter {
        val filter = ListFilter()
        reader.beginObject()
        var fieldName: String? = null

        while (reader.hasNext()) {
            var token: JsonToken = reader.peek()
            if (token == JsonToken.NAME) {
                //get the current token
                fieldName = reader.nextName()
            }
            if (FILTER_TYPE == fieldName) {
                //move to next token
                token = reader.peek()
                filter.type = FilterType.getById(reader.nextInt())
            }
            if (PERIOD_FROM == fieldName) {
                //move to next token
                token = reader.peek()
                filter.periodFrom = reader.nextInt()
            }
            if (PERIOD_TO == fieldName) {
                //move to next token
                token = reader.peek()
                filter.periodTo = reader.nextInt()
            }
            if (SORTING_TYPE == fieldName) {
                //move to next token
                token = reader.peek()
                filter.sortingType = SortingType.getById(reader.nextInt())
            }
        }
        reader.endObject()
        return filter
    }
}