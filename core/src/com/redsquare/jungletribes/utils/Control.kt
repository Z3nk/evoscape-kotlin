package com.redsquare.jungletribes.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

class Control(// SCREEN
        internal var screenWidth: Int, internal var screenHeight: Int, // CAMERA
        internal var camera: OrthographicCamera?) : InputAdapter(), InputProcessor {

    // DIRECTIONS
    var up: Boolean = false
    var down: Boolean = false
    var left: Boolean = false
    var right: Boolean = false

    // ACTIONS
    var interact: Boolean = false

    // MOUSE
    var leftMouseBtn: Boolean = false
    var rightMouseBtn: Boolean = false
    var processedClick: Boolean = false
    var mouseClickPos = Vector2()
    var mapClickPos = Vector2()

    // DEBUG
    var debug: Boolean = false
    var reset: Boolean = false

    private fun setMouseClickedPos(screenX: Int, screenY: Int) {
        // Set mouse position (flip screen Y)
        mouseClickPos.set(screenX.toFloat(), (screenHeight - screenY).toFloat())
        mapClickPos.set(get_map_coords(mouseClickPos))
    }

    fun get_map_coords(mouseCoords: Vector2): Vector2 {
        val v3 = Vector3(mouseCoords.x, screenHeight - mouseCoords.y, 0f)
        this.camera?.unproject(v3)?:println("camera is null")
        return Vector2(v3.x, v3.y)
    }

    override fun keyDown(keyCode: Int): Boolean {
        when (keyCode) {
            Keys.DOWN -> down = true
            Keys.UP -> up = true
            Keys.LEFT -> left = true
            Keys.RIGHT -> right = true
            Keys.Z -> up = true
            Keys.Q -> left = true
            Keys.S -> down = true
            Keys.D -> right = true
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Keys.DOWN -> down = false
            Keys.UP -> up = false
            Keys.LEFT -> left = false
            Keys.RIGHT -> right = false
            Keys.Z -> up = false
            Keys.Q -> left = false
            Keys.S -> down = false
            Keys.D -> right = false
            Keys.E -> interact = true
            Keys.ESCAPE -> Gdx.app.exit()
            Keys.BACKSPACE -> debug = !debug
            Keys.R -> reset = true
        }
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == 0 && button == 0) {
            leftMouseBtn = true
        } else if (pointer == 0 && button == 0) {
            rightMouseBtn = true
        }

        setMouseClickedPos(screenX, screenY)
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == 0 && button == 0) {
            leftMouseBtn = false
            processedClick = false
        } else if (pointer == 0 && button == 0) {
            rightMouseBtn = false
        }

        setMouseClickedPos(screenX, screenY)
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        setMouseClickedPos(screenX, screenY)
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

}
