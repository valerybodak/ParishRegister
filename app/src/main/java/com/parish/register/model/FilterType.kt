package com.parish.register.model

enum class FilterType(val id: Int) {
    NO_FILTERS(0),
    BORN(1),
    MARRIAGE(2),
    DIED(3);

    companion object {
        fun getById(id: Int?): FilterType {
            if (id == null) {
                return NO_FILTERS
            }
            return values()
                .find { it.id == id } ?: NO_FILTERS
        }
    }
}