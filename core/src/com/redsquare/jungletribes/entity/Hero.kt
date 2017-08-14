package com.redsquare.jungletribes.entity

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.redsquare.jungletribes.box2d.Box2DHelper
import com.redsquare.jungletribes.box2d.Box2DWorld
import com.redsquare.jungletribes.utils.Control
import com.redsquare.jungletribes.utils.Enums
import com.redsquare.jungletribes.utils.Media

import java.util.ArrayList

class Hero(pos: Vector3, box2d: Box2DWorld) : Entity(Media.hero) {
    internal lateinit var interactEntities: ArrayList<Entity>

    init {
        type = Enums.EntityType.HERO
        width = 8.0f
        height = 8.0f
        speed = 30.0f
        reset(box2d, pos)
    }

    fun reset(box2d: Box2DWorld, pos: Vector3) {
        this.pos.set(pos)
        body = Box2DHelper.createBody(box2d.world, width / 2, height / 2, width / 4, 0.0f, pos, BodyType.DynamicBody)
        hashcode = body!!.fixtureList.get(0).hashCode()
        interactEntities = ArrayList<Entity>()
    }

    fun update(control: Control) {
        dirX = 0.0f
        dirY = 0.0f

        if (control.down) dirY = -1.0f
        if (control.up) dirY = 1.0f
        if (control.left) dirX = -1.0f
        if (control.right) dirX = 1.0f

        body!!.setLinearVelocity(dirX * speed, dirY * speed)
        pos.x = body!!.position.x - width / 2
        pos.y = body!!.position.y - height / 4

        // If interact key pressed and interactEntities present interact with first in list.
        if (control.interact && interactEntities.size > 0) {
            interactEntities[0].interact()
        }

        // Reset interact
        control.interact = false
    }

    override fun collision(entity: Entity, begin: Boolean) {
        if (begin) {
            // Hero entered hitbox
            interactEntities.add(entity)
        } else {
            // Hero Left hitbox
            interactEntities.remove(entity)
        }
    }

}