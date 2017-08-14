package com.redsquare.jungletribes.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Body
import com.redsquare.jungletribes.box2d.Box2DWorld
import com.redsquare.jungletribes.utils.Enums

open class Entity(var texture: Texture) : Comparable<Entity> {
    var hashcode: Int = 0
    var pos: Vector3
    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()
    var type: Enums.EntityType? = null
    var speed: Float = 0.toFloat()
    var body: Body? = null
    var sensor: Body? = null
    var remove: Boolean = false

    internal var dirX = 0f
    internal var dirY = 0f

    init {
        pos = Vector3()
    }

    fun draw(batch: SpriteBatch) {
        batch.draw(texture, pos.x, pos.y, width, height)
    }

    override fun compareTo(e: Entity): Int {
        val tempY = e.pos.y
        val compareY = pos.y

        return if (tempY < compareY) -1 else if (tempY > compareY) 1 else 0
    }

    open fun collision(entity: Entity, begin: Boolean) {}

    open fun interact() {}

    fun removeBodies(box2D: Box2DWorld) {
        if (sensor != null) box2D.world.destroyBody(sensor)
        if (body != null) box2D.world.destroyBody(body)
    }

}
