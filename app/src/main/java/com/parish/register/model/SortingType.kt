package com.parish.register.model

enum class SortingType(val id: Int) {
    BY_DATE_ASC(0),
    BY_DATE_DESC(1),
    BY_NAME(2);

    companion object {
        fun getById(id: Int?): SortingType {
            if (id == null) {
                return BY_DATE_ASC
            }
            return values()
                .find { it.id == id } ?: BY_DATE_ASC
        }
    }
}