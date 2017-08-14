package com.redsquare.jungletribes.map

import com.badlogic.gdx.graphics.Texture
import com.redsquare.jungletribes.entity.Entity
import com.redsquare.jungletribes.utils.Enums

class Tile(x: Float, y: Float, var size: Float, var tileType: Enums.TileType, texture: Texture) : Entity(texture) {
    var row: Int = 0
    var col: Int = 0
    var code: String
    var secondaryTexture: Texture? = null

    init {
        pos.x = x * size
        pos.y = y * size
        this.col = x.toInt()
        this.row = y.toInt()
        this.code = ""
    }

    fun details(): String {
        return "x: " + pos.x + " y: " + pos.y + " row: " + row + " col: " + col + " code: " + code + " type: " + type.toString()
    }

    val isGrass: Boolean
        get() = tileType === Enums.TileType.GRASS

    val isWater: Boolean
        get() = tileType === Enums.TileType.WATER

    val isCliff: Boolean
        get() = tileType === Enums.TileType.CLIFF

    val isPassable: Boolean
        get() = !isWater && !isCliff

    val isNotPassable: Boolean
        get() = !isPassable

    val isAllWater: Boolean
        get() = code == "000000000"

    fun notIsAllWater(): Boolean {
        return !isAllWater
    }
}
