package com.zhangyf.draftbottle.util

import android.graphics.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zhangyf.draftbottle.R
import java.io.ByteArrayOutputStream

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.color.colorWhite)
        .skipMemoryCache(false)
        .centerCrop()
        .into(imageView)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: Int?) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.color.colorWhite)
        .skipMemoryCache(false)
        .into(imageView)
}

fun rotateYUV420Degree90(
    data: ByteArray,
    imageWidth: Int,
    imageHeight: Int
): ByteArray? {
    val yuv = ByteArray(imageWidth * imageHeight * 3 / 2)
    var i = 0
    for (x in 0 until imageWidth) {
        for (y in imageHeight - 1 downTo 0) {
            yuv[i] = data[y * imageWidth + x]
            i++
        }
    }
    i = imageWidth * imageHeight * 3 / 2 - 1
    var x = imageWidth - 1
    while (x > 0) {
        for (y in 0 until imageHeight / 2) {
            yuv[i] = data[imageWidth * imageHeight + y * imageWidth + x]
            i--
            yuv[i] = data[imageWidth * imageHeight + y * imageWidth
                    + (x - 1)]
            i--
        }
        x = x - 2
    }
    return yuv
}

fun rotateYUV420Degree180(
    data: ByteArray,
    imageWidth: Int,
    imageHeight: Int
): ByteArray? {
    val yuv = ByteArray(imageWidth * imageHeight * 3 / 2)
    var i = 0
    var count = 0
    i = imageWidth * imageHeight - 1
    while (i >= 0) {
        yuv[count] = data[i]
        count++
        i--
    }
    i = imageWidth * imageHeight * 3 / 2 - 1
    i = imageWidth * imageHeight * 3 / 2 - 1
    while (i >= imageWidth
        * imageHeight
    ) {
        yuv[count++] = data[i - 1]
        yuv[count++] = data[i]
        i -= 2
    }
    return yuv
}

fun rotateYUV420Degree270(
    data: ByteArray, imageWidth: Int,
    imageHeight: Int
): ByteArray? {
    val yuv = ByteArray(imageWidth * imageHeight * 3 / 2)
    var nWidth = 0
    var nHeight = 0
    var wh = 0
    var uvHeight = 0
    if (imageWidth != nWidth || imageHeight != nHeight) {
        nWidth = imageWidth
        nHeight = imageHeight
        wh = imageWidth * imageHeight
        uvHeight = imageHeight shr 1 // uvHeight = height / 2
    }
    // ??Y
    var k = 0
    for (i in 0 until imageWidth) {
        var nPos = 0
        for (j in 0 until imageHeight) {
            yuv[k] = data[nPos + i]
            k++
            nPos += imageWidth
        }
    }
    var i = 0
    while (i < imageWidth) {
        var nPos = wh
        for (j in 0 until uvHeight) {
            yuv[k] = data[nPos + i]
            yuv[k + 1] = data[nPos + i + 1]
            k += 2
            nPos += imageWidth
        }
        i += 2
    }
    return rotateYUV420Degree180(yuv, imageWidth, imageHeight)
}

fun rotateImageDegree270(
    data: ByteArray,
    width: Int,
    height: Int
): ByteArray {
    val rotateBitmap = rotateBitmap(Bytes2Bimap(data,width,height), 270f)
    //Bitmap转换成byte[]
    val baos = ByteArrayOutputStream()
    rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}

fun Bytes2Bimap(
    bs: ByteArray,
    width: Int,
    height: Int
): Bitmap {

    val yuvimage = YuvImage(bs, ImageFormat.NV21, width, height, null)

    val baos = ByteArrayOutputStream()
    yuvimage.compressToJpeg(Rect(0, 0, 20, 20), 100, baos)

    val jdata = baos.toByteArray()

    return BitmapFactory.decodeByteArray(jdata, 0, jdata.size)
}

fun rotateBitmap(bitmap: Bitmap, degress: Float): Bitmap {
    val m = Matrix()
    m.postRotate(degress)
    val bitmapNew =
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true)
    return bitmapNew
}

