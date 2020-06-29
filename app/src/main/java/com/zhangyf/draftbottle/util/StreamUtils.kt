package com.zhangyf.draftbottle.util

import android.content.Context
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object StreamUtils {
    fun toString(con: Context, assetFileName: String, bufSizeHint: Int): String? {
        return try {
            toString(con.assets.open(assetFileName), bufSizeHint)
        } catch (e: Exception) {
            DP.p(StreamUtils::class.java, "toString", e)
            null
        }
    }

    fun toString(`in`: InputStream, bufSizeHint: Int): String? {
        try {
            val reader = BufferedReader(InputStreamReader(`in`))

            val sb = StringBuilder()
            var len = 0
            val buf = CharArray(if (bufSizeHint <= 0) 512 else bufSizeHint)
            do {
                len = reader.read(buf)
                if (len > 0)
                    sb.append(buf, 0, len)
                else
                    break
            } while (len >= 0)

            return sb.toString()
        } catch (e: Exception) {
            DP.p(StreamUtils::class.java, "toString", e)
            return null
        }

    }

    fun toString(f: File, bufSizeHint: Int): String? {
        try {
            val reader = BufferedReader(FileReader(f))

            val sb = StringBuilder()

            var len = 0
            val buf = CharArray(if (bufSizeHint <= 0) 512 else bufSizeHint)
            do {
                len = reader.read(buf)
                if (len > 0)
                    sb.append(buf, 0, len)
                else
                    break
            } while (len >= 0)


            return sb.toString()
        } catch (e: Exception) {
            DP.p(StreamUtils::class.java, "toString", e)
            return null
        }

    }

    fun toBytes(f: File, bufSizeHint: Int): ByteArray? {
        try {
            if (f.exists())
                f.delete()

            val fs = FileInputStream(f)
            val bas = ByteArrayOutputStream()

            var len = 0
            val buf = ByteArray(if (bufSizeHint <= 0) 512 else bufSizeHint)
            do {
                len = fs.read(buf)
                if (len > 0)
                    bas.write(buf, 0, len)
                else
                    break
            } while (len >= 0)


            return bas.toByteArray()
        } catch (e: Exception) {
            DP.p(StreamUtils::class.java, "toBytes", e)
            return null
        }

    }

    fun writeToFile(f: File, s: String): Boolean {
        try {
            if (f.exists())
                f.delete()

            val w = FileWriter(f)
            w.write(s)
            w.close()

            return true
        } catch (e: Exception) {
            DP.p(StreamUtils::class.java, "writeToFile", e)
            return false
        }

    }

    fun writeToFile(f: File, b: ByteArray): Boolean {
        try {
            if (f.exists())
                f.delete()

            val stm = FileOutputStream(f)
            stm.write(b)
            stm.close()

            return true
        } catch (e: Exception) {
            DP.p(StreamUtils::class.java, "writeToFile", e)
            return false
        }

    }

    fun compress(str: String): ByteArray? {
        val bytes = ByteArrayOutputStream()
        val data = ZipOutputStream(bytes)

        val en = ZipEntry("entry")
        try {
            data.putNextEntry(en)
            data.write(str.toByteArray(charset("utf-8")))
            data.close()

            return bytes.toByteArray()
        } catch (e: Exception) {
            return null
        }

    }

    fun decompress(zip: ByteArray): String? {
        val bytes = ByteArrayInputStream(zip)
        val data = ZipInputStream(bytes)

        //		final ZipEntry en = new ZipEntry(name);
        try {
            data.nextEntry ?: return null

            var len = 0
            val out = ByteArrayOutputStream()
            val buf = ByteArray(512)
            do {
                len = data.read(buf)
                if (len > 0)
                    out.write(buf, 0, len)
                else
                    break
            } while (len >= 0)

            data.close()
            return out.toString("utf-8")
        } catch (e: Exception) {
            return null
        }

    }
}
