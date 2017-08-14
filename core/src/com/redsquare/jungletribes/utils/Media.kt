package com.redsquare.jungletribes.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class Media {

    fun dispose() {
        grass01.dispose()
        grass02.dispose()
        grass03.dispose()
        grass04.dispose()
        grassLeft.dispose()
        grassRight.dispose()
        grassLeftUpperEdge.dispose()
        grassRightUpperEdge.dispose()
        grassTop.dispose()
        grassTopRight.dispose()
        grassTopLeft.dispose()
        water01.dispose()
        water02.dispose()
        water03.dispose()
        water04.dispose()
        cliff.dispose()
        tree.dispose()
    }

    companion object {
        // TILES
        lateinit var grass01: Texture
        lateinit var grass02: Texture
        lateinit var grass03: Texture
        lateinit var grass04: Texture
        lateinit var grassLeft: Texture
        lateinit var grassRight: Texture
        lateinit var grassLeftUpperEdge: Texture
        lateinit var grassRightUpperEdge: Texture
        lateinit var grassTop: Texture
        lateinit var grassTopRight: Texture
        lateinit var grassTopLeft: Texture
        lateinit var water01: Texture
        lateinit var water02: Texture
        lateinit var water03: Texture
        lateinit var water04: Texture
        lateinit var cliff: Texture

        // HERO
        lateinit var hero: Texture

        // Entity
        lateinit var tree: Texture

        fun load_assets() {
            // HERO
            hero = Texture("entities/hero/hero.png")

            // Source https://opengameart.org/content/micro-tileset-overworld-and-dungeon
            // Example Map: http://opengameart.org/sites/default/files/styles/watermarked/public/Render_0.png
            grass01 = Texture("8x8/grass/grass_01.png")
            grass02 = Texture("8x8/grass/grass_02.png")
            grass03 = Texture("8x8/grass/grass_03.png")
            grass04 = Texture("8x8/grass/grass_04.png")

            grassLeft = Texture("8x8/grass/right_grass_edge.png")
            grassRight = Texture("8x8/grass/left_grass_edge.png")

            grassLeftUpperEdge = Texture("8x8/grass/left_upper_edge.png")
            grassRightUpperEdge = Texture("8x8/grass/right_upper_edge.png")

            grassTop = Texture("8x8/grass/top.png")
            grassTopRight = Texture("8x8/grass/top_right.png")
            grassTopLeft = Texture("8x8/grass/top_left.png")

            water01 = Texture("8x8/water/water_01.png")
            water02 = Texture("8x8/water/water_02.png")
            water03 = Texture("8x8/water/water_03.png")
            water04 = Texture("8x8/water/water_04.png")
            cliff = Texture(Gdx.files.internal("8x8/cliff.png"))

            tree = Texture("entities/tree.png")
        }
    }
}
