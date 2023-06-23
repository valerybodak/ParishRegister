package com.parish.register.utils

import java.util.*

/**
 * This class is based on the following example:
 * https://stackoverflow.com/a/16018452/1225669
 */
object StringComparator {

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    fun similarity(s1: String, s2: String): Double {
        var longer = s1
        var shorter = s2
        if (s1.length < s2.length) { // longer should always have greater length
            longer = s2
            shorter = s1
        }
        val longerLength = longer.length
        return if (longerLength == 0) {
            1.0 /* both strings are zero length */
        } else (longerLength - editDistance(
            longer,
            shorter
        )) / longerLength.toDouble()
        /* // If you have Apache Commons Text, you can use it to calculate the edit distance:
            LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
            return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double) longerLength; */
    }

    /**
     * The editDistance() function below is expected to
     * calculate the edit distance between the two strings.
     * There are several implementations to this step,
     * each may suit a specific scenario better. The most common
     * is the Levenshtein distance algorithm and we'll use
     * it here (for very large strings, other algorithms are
     * likely to perform better).
     */
    private fun editDistance(str1: String, str2: String): Int {
        val s1 = str1.lowercase(Locale.getDefault())
        val s2 = str2.lowercase(Locale.getDefault())
        val costs = IntArray(s2.length + 1)
        for (i in 0..s1.length) {
            var lastValue = i
            for (j in 0..s2.length) {
                if (i == 0) costs[j] = j else {
                    if (j > 0) {
                        var newValue = costs[j - 1]
                        if (s1[i - 1] != s2[j - 1]) newValue = Math.min(
                            Math.min(newValue, lastValue),
                            costs[j]
                        ) + 1
                        costs[j - 1] = lastValue
                        lastValue = newValue
                    }
                }
            }
            if (i > 0) costs[s2.length] = lastValue
        }
        return costs[s2.length]
    }
}