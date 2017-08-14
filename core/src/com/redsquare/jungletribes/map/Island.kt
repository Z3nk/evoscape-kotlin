package com.redsquare.jungletribes.map

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.redsquare.jungletribes.box2d.Box2DHelper
import com.redsquare.jungletribes.box2d.Box2DWorld
import com.redsquare.jungletribes.entity.Entity
import com.redsquare.jungletribes.entity.Tree
import com.redsquare.jungletribes.utils.Enums
import com.redsquare.jungletribes.utils.Media

import java.util.ArrayList
import java.util.Arrays

class Island(box2D: Box2DWorld) {
    lateinit var centreTile: Tile
    internal var clickedTile: Tile? = null

    // CHUNKS TODO: Add multiple chunks
    // public Map<Integer, ArrayList<Chunk> chunks = new Map<Integer, ArrayList<Chunk>();

    // ONE CHUNK
    lateinit var chunk: Chunk
    var entities = ArrayList<Entity>()

    // TRACK CLICK
    internal var currentTileNo: Int = 0
    internal var currentCol: Int = 0
    internal var currentRow: Int = 0

    // Arrays for mapping code to texture
    internal var aGrassLeft = arrayOf("001001001", "001001001", "001001000", "000001001")
    internal var aGrassRight = arrayOf("100100100", "100100000", "000100100")
    internal var aGrassREnd = arrayOf("100000000")
    internal var aGrassLEnd = arrayOf("001000000")
    internal var aGrassTop = arrayOf("000000111", "000000011", "000000110")
    internal var aGrassTopRight = arrayOf("000000100")
    internal var aGrassTopLeft = arrayOf("000000001")

    init {
        reset(box2D)
    }

    fun reset(box2D: Box2DWorld) {
        entities.clear()
        box2D.clearAllBodies()
        setupTiles()
        codeTiles()
        generateHitboxes(box2D)
        addEntities(box2D)
    }

    private fun generateHitboxes(box2D: Box2DWorld) {
        for (row in chunk.tiles) {
            for (tile in row) {
                if (tile.isNotPassable && tile.notIsAllWater()) {
                    Box2DHelper.createBody(box2D.world, chunk.tileSize, chunk.tileSize, 0.0f, 0.0f, tile.pos, BodyType.StaticBody)
                }
            }
        }
    }

    private fun setupTiles() {
        chunk = Chunk(33, 33, 8.0f)

        var currentRow = 0
        val rngW = MathUtils.random(5, 8)
        val rngH = MathUtils.random(5, 8)

        val centreTileRow = chunk.numberRows / 2
        val centreTileCol = chunk.numberCols / 2
        val firstTileRow = centreTileRow - rngH

        val maxRow = centreTileRow + rngH
        val minRow = centreTileRow - rngH
        val maxCol = centreTileCol + rngW
        val minCol = centreTileCol - rngW

        // CHUNK ROW
        var chunkRow = ArrayList<Tile>()

        // If number of tiles is needed.
        // int num_tiles = ((max_col - min_col)-1) * ((max_row - min_row)-1);

        for (row in 0..chunk.numberRows - 1) {
            for (col in 0..chunk.numberCols - 1) {
                // Create TILE
                val tile = Tile(col.toFloat(), row.toFloat(), chunk.tileSize, Enums.TileType.WATER, randomWater())

                // Make a small island
                if (row > minRow && row < maxRow && col > minCol && col < maxCol) {
                    tile.texture = randomGrass()
                    tile.tileType = Enums.TileType.GRASS

                    if (row == firstTileRow + 1) {
                        tile.texture = Media.cliff
                        tile.tileType = Enums.TileType.CLIFF
                    } else {
                        // Chance to add trees etc
                    }
                }

                // ADD TILE TO CHUNK
                if (currentRow == row) {
                    // Add tile to current row
                    chunkRow.add(tile)

                    // Last row and column?
                    if (row == chunk.numberRows - 1 && col == chunk.numberCols - 1) {
                        chunk.tiles.add(chunkRow)
                    }
                } else {
                    // New row
                    currentRow = row

                    // Add row to chunk
                    chunk.tiles.add(chunkRow)

                    // Clear chunk row
                    chunkRow = ArrayList<Tile>()

                    // Add first tile to the new row
                    chunkRow.add(tile)
                }
            }
        }

        // Set centre tile for camera positioning
        centreTile = chunk?.getTile(centreTileRow, centreTileCol)?: return
    }

    private fun updateImage(tile: Tile) {
        // Secondary Texture is to add edges to tiles
        // TODO: Add array of textures per tile
        if (Arrays.asList(*aGrassLeft).contains(tile.code)) {
            tile.secondaryTexture = Media.grassLeft
        } else if (Arrays.asList(*aGrassRight).contains(tile.code)) {
            tile.secondaryTexture = Media.grassRight
        } else if (Arrays.asList(*aGrassREnd).contains(tile.code)) {
            tile.secondaryTexture = Media.grassLeftUpperEdge
        } else if (Arrays.asList(*aGrassLEnd).contains(tile.code)) {
            tile.secondaryTexture = Media.grassRightUpperEdge
        } else if (Arrays.asList(*aGrassTop).contains(tile.code)) {
            tile.secondaryTexture = Media.grassTop
        } else if (Arrays.asList(*aGrassTopRight).contains(tile.code)) {
            tile.secondaryTexture = Media.grassTopRight
        } else if (Arrays.asList(*aGrassTopLeft).contains(tile.code)) {
            tile.secondaryTexture = Media.grassTopLeft
        }
    }

    private fun randomGrass(): Texture {
        val grass: Texture

        val tile = MathUtils.random(20)
        when (tile) {
            1 -> grass = Media.grass01
            2 -> grass = Media.grass02
            3 -> grass = Media.grass03
            4 -> grass = Media.grass04
            else -> grass = Media.grass01
        }

        return grass
    }

    private fun randomWater(): Texture {
        val water: Texture

        val tile = MathUtils.random(20)
        when (tile) {
            1 -> water = Media.water01
            2 -> water = Media.water02
            3 -> water = Media.water03
            4 -> water = Media.water04
            else -> water = Media.water01
        }

        return water
    }

    private fun codeTiles() {
        // Loop all tiles and set the initial code

        // 1 CHUNK ONLY ATM
        for (row in chunk.tiles) {
            for (tile in row) {
                // Check all surrounding tiles and set 1 for pass 0 for non pass
                // 0 0 0
                // 0 X 0
                // 0 0 0

                val rows = intArrayOf(1, 0, -1)
                val cols = intArrayOf(-1, 0, 1)

                for (r in rows) {
                    for (c in cols) {
                        tile.code = tile.code + chunk.getTileCode(tile.row + r, tile.col + c)
                        updateImage(tile)
                    }
                }
            }
        }
    }

    private fun addEntities(box2D: Box2DWorld) {
        // Loop all tiles and add random trees
        for (row in chunk.tiles) {
            for (tile in row) {
                if (tile.isGrass) {
                    if (MathUtils.random(100) > 90) {
                        entities.add(Tree(tile.pos, box2D))
                    }
                }
            }
        }
    }

    val centrePosition: Vector3
        get() = centreTile.pos

    fun dispose() {

    }

    fun clearRemovedEntities(box2D: Box2DWorld) {
        val it = entities.iterator()
        while (it.hasNext()) {
            val e = it.next()
            if (e.remove) {
                e.removeBodies(box2D)
                box2D.removeEntityToMap(e)

                it.remove()
            }
        }
    }

}
