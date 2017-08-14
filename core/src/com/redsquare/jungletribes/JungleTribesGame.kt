package com.redsquare.jungletribes

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.redsquare.jungletribes.box2d.Box2DWorld
import com.redsquare.jungletribes.entity.Hero
import com.redsquare.jungletribes.map.Island
import com.redsquare.jungletribes.utils.Control
import com.redsquare.jungletribes.utils.Media
import java.util.*


class JungleTribesGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var box2D: Box2DWorld
    lateinit var hero: Hero
    lateinit var island: Island

    lateinit var camera: OrthographicCamera
    lateinit var control: Control

    // Display Size
    private var displayW: Int = 0
    private var displayH: Int = 0

    override fun create() {
        Media.load_assets()
        batch = SpriteBatch()

        // CAMERA
        displayW = Gdx.graphics.width
        displayH = Gdx.graphics.height

        // For 800x600 we will get 266*200
        val h:Float = (displayH/Math.floor(displayH/160.0)).toFloat()
        val w:Float = (displayW/(displayH/ (displayH/Math.floor(displayH/160.0)))).toFloat()

        camera = OrthographicCamera(w,h)
        camera.zoom = .4f

        // Used to capture Keyboard Input
        control = Control(displayW, displayH, camera)
        Gdx.input.inputProcessor = control

        // Box2D
        box2D = Box2DWorld()

        // Island
        island = Island(box2D)

        // Hero
        hero = Hero(island.centreTile.pos, box2D)
        island.entities.add(hero)

        // HashMap of Entities for collisions
        box2D.populateEntityMap(island.entities)

    }

    override fun render() {

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // GAME LOGIC
        if (control.reset) {
            island.reset(box2D)
            hero.reset(box2D, island.centrePosition)
            island.entities.add(hero)
            box2D.populateEntityMap(island.entities)
            control.reset = false
        }

        hero.update(control)

        camera.position.lerp(hero.pos, .1f)
        camera.update()
        Collections.sort(island.entities)

        // GAME DRAW
        batch.projectionMatrix = camera.combined
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        batch.begin()
        // Draw all tiles in the chunk / chunk rows
        for (row in island.chunk.tiles) {
            for (tile in row) {
                batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size)
                if (tile.secondaryTexture != null) batch.draw(tile.secondaryTexture, tile.pos.x, tile.pos.y, tile.size, tile.size)
            }
        }

        // Draw all entities
        for (e in island.entities) {
            e.draw(batch)
        }
        batch.end()

        box2D.tick(camera, control)
        island.clearRemovedEntities(box2D)

    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
