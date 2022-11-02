package com.parish.register.model

data class ListFilter(
    var filterType: FilterType = FilterType.NO_FILTERS,
    var periodFrom: Int = 0,
    var periodTo: Int = 0
)