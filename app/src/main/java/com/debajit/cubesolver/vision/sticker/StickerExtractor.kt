package com.debajit.cubesolver.vision.sticker

import android.graphics.Bitmap

object StickerExtractor {

    fun extract(
        bitmap: Bitmap
    ): List<StickerImage> {

        val width = bitmap.width
        val height = bitmap.height

        val gridSize = (minOf(width, height) * 0.60f).toInt()

        val left = ((width - gridSize) / 2f).toInt()
        val top = ((height - gridSize) / 2f).toInt()

        val cell = gridSize / 3

        val stickers = mutableListOf<StickerImage>()

        for (row in 0 until 3) {

            for (col in 0 until 3) {

                val x = left + col * cell
                val y = top + row * cell

                val margin = (cell * 0.25f).toInt()

                val stickerBitmap = Bitmap.createBitmap(
                    bitmap,
                    x + margin,
                    y + margin,
                    cell - margin * 2,
                    cell - margin * 2
                )

                stickers.add(
                    StickerImage(
                        bitmap = stickerBitmap,
                        row = row,
                        col = col
                    )
                )

            }

        }

        return stickers

    }

}