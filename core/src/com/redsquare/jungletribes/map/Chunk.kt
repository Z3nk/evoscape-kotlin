package com.redsquare.jungletribes.map

import java.util.ArrayList

class Chunk(internal var numberRows: Int, internal var numberCols: Int, internal var tileSize: Float) {
    // Tiles are split into arrays of rows
    var tiles = ArrayList<ArrayList<Tile>>()

    init {
        tiles = ArrayList<ArrayList<Tile>>()
    }

    fun getTile(row: Int, col: Int): Tile? {
        val chunk_row: ArrayList<Tile>?
        if (tiles.size > row && row >= 0) {
            chunk_row = tiles[row]

            if (chunk_row != null && chunk_row.size > col && col >= 0) {
                return chunk_row[col]
            }
        }
        return null
    }

    fun getTileCode(row: Int, col: Int): String {
        val tile: Tile

        val chunk_row: ArrayList<Tile>?
        if (tiles.size > row && row >= 0) {
            chunk_row = tiles[row]

            if (chunk_row != null && chunk_row.size > col && col >= 0) {
                tile = chunk_row[col]
                return if (tile.isGrass) "1" else "0"
            }
        }
        return "0"
    }

}