package com.parish.register.model

enum class FilterType(val id: Int) {
    ALL(0),
    BORN(1),
    MARRIAGES(2),
    DIED(3);

    companion object {
        fun getById(id: Int?): FilterType {
            if (id == null) {
                return ALL
            }
            return values()
                .find { it.id == id } ?: ALL
        }
    }
}