package com.parish.register.nineteen.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.*
import java.util.*
import kotlin.coroutines.resume

object FirebaseHelper {

    private const val FILE_LINES_DIVIDER = "\n"

    /*suspend fun getFirebaseFileData(filePath: String): FileData =
        suspendCancellableCoroutine { continuation ->

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            val pathReference = storageRef.child(filePath)

            val tempFileName = "temp_file_" + Calendar.getInstance().timeInMillis
            val localFile: File = File.createTempFile(tempFileName, "txt")

            pathReference.getFile(localFile)
                .addOnSuccessListener {
                    readFile(localFile,
                        { linesNumber, fileText ->
                            continuation.resume(
                                FileData(
                                    filePath,
                                    fileText,
                                    linesNumber
                                )
                            )
                        },
                        { exception ->
                            continuation.cancel(exception)
                        }
                    )
                }
                .addOnFailureListener { exception ->
                    continuation.cancel(exception)
                }
        }

    fun <ResultType> loadFileData(
        fileName: String,
        query: suspend () -> List<ResultType>,
        shouldFetch: (List<ResultType>) -> Boolean,
        observeProgress: Boolean = false,
        saveFetchResponse: suspend (List<String>) -> Unit,
        onFetchFailed: (Throwable) -> Unit = { },
    ): Flow<Resource<List<ResultType>>> = flow {
        emit(Resource.Loading())

        val data = query()

        if (shouldFetch(data)) {
            try {
                val fileData = getFirebaseFileData(fileName)
                val fileLines = fileData.text.split("\n")
                val resultItems: MutableList<String> = mutableListOf()
                for (index in fileLines.indices) {
                    val line = fileLines[index]
                    if (observeProgress) {
                        //we set the small delay just to avoid too fast progress
                        delay(1)
                        emit(Resource.Loading(ProgressData(index, fileLines.size)))
                    }
                    resultItems.add(line)
                }
                saveFetchResponse(resultItems)
                emit(Resource.Success(query()))
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                emit(Resource.Error(0, throwable.message.toString(), query()))
            }
        } else {
            emit(Resource.Success(data))
        }
    }

    private fun readFile(
        file: File,
        success: (linesNumber: Int, fileText: String) -> Unit,
        error: (exception: Exception) -> Unit
    ) {
        val linesInFile = calculateFileLines(file)
        val sb = StringBuilder()
        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            while (br.readLine().also { line = it } != null) {

                if (line != null && line!!.isNotEmpty()) {
                    sb.append(line!!)
                    sb.append(FILE_LINES_DIVIDER)
                }
            }
            br.close()
            success(linesInFile, sb.toString())
        } catch (e: Exception) {
            error(e)
        }
    }

    private fun calculateFileLines(file: File): Int {
        val bufferedReader = BufferedReader(FileReader(file))
        val lineNumberReader = LineNumberReader(bufferedReader)
        try {
            lineNumberReader.skip(Long.MAX_VALUE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return lineNumberReader.lineNumber + 1
    }*/
}