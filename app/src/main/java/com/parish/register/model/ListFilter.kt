package com.parish.register.model

data class ListFilter(
    var type: FilterType = FilterType.NO_FILTERS,
    var periodFrom: Int = DEFAULT_PERIOD_FROM,
    var periodTo: Int = DEFAULT_PERIOD_TO
)

const val DEFAULT_PERIOD_FROM = 1700
const val DEFAULT_PERIOD_TO = 1945