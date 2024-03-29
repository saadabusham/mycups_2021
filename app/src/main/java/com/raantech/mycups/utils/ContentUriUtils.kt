package com.raantech.mycups.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.text.TextUtils
import java.io.*


object ContentUriUtils {

    private const val BUFFER_SIZE = 1024 * 2
    private const val IMAGE_DIRECTORY = "/mycups_upload"

    fun Uri.getFilePathFromURI(context: Context): String? {
        val fileName = context.getFileName(this)
        val wallpaperDirectory = File(
            context.getExternalFilesDir(null)?.absolutePath + IMAGE_DIRECTORY
        )
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(wallpaperDirectory.path + File.separator.toString() + fileName)
            // create folder if not exists
            copy(context, this, copyFile)
            return copyFile.absolutePath
        }
        return null
    }

    fun Context.getFileName(uri: Uri?): String? {
        if (uri == null) return null
        var fileName: String? = null
        val path = uri.getName(this)
        val cut = path!!.lastIndexOf('/')
        fileName = path.substring(cut + 1)
//        if(fileName?.contains(".pdf") == false)
//            fileName = "$fileName.pdf"
        return fileName
    }

    fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri!!)
                ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            copystream(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @Throws(java.lang.Exception::class, IOException::class)
    fun copystream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(BUFFER_SIZE)
        val `in` = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, BUFFER_SIZE).also({ n = it }) != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
//                Log.e(e.getMessage(), java.lang.String.valueOf(e))
                e
            }
            try {
                `in`.close()
            } catch (e: IOException) {
//                Log.e(e.getMessage(), java.lang.String.valueOf(e))
                e
            }
        }
        return count
    }

    fun Uri.getName(context: Context): String? {
        context.contentResolver.query(this, null, null, null, null)
            ?.use { cursor ->
                /*
                 * Get the column indexes of the data in the Cursor,
                 * move to the first row in the Cursor, get the data,
                 * and display it.
                 */
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                return cursor.getString(nameIndex)
            } ?: return ""
    }
}