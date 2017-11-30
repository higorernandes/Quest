package loremipsumvirtualenterprise.quest.util

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Created by root on 2017-11-29.
 */
class CircleTransform constructor(context: Context) : BitmapTransformation(context)
{
    //region Overridden Methods

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return circleCrop(pool, toTransform)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest?) { }

    //endregion

    //region Companion Object

    companion object {
        fun circleCrop(pool: BitmapPool, source: Bitmap?) : Bitmap? {
            if (source == null) return null

            val size : Int = Math.min(source.width, source.height)
            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            val squared : Bitmap = Bitmap.createBitmap(source, x, y, size, size)

            var result : Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)

            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            }

            val canvas : Canvas = Canvas(result)
            val paint : Paint = Paint()
            paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val r : Float = size / 2.0f
            canvas.drawCircle(r, r, r, paint)

            return result
        }
    }

    //endregion
}