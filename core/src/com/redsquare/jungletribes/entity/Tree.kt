package com.redsquare.jungletribes.entity

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.BodyDef
import com.redsquare.jungletribes.box2d.Box2DHelper
import com.redsquare.jungletribes.box2d.Box2DWorld
import com.redsquare.jungletribes.utils.Enums
import com.redsquare.jungletribes.utils.Media

class Tree(pos: Vector3, box2d: Box2DWorld) : Entity(Media.tree) {

    init {
        type = Enums.EntityType.TREE
        width = 8.0f
        height = 8.0f
        this.pos = pos
        body = Box2DHelper.createBody(box2d.world, width / 2, height / 2, width / 4, 0.0f, pos, BodyDef.BodyType.StaticBody)
        sensor = Box2DHelper.createSensor(box2d.world, width, height * .85f, width / 2, height / 3, pos, BodyDef.BodyType.DynamicBody)
        hashcode = sensor!!.fixtureList.get(0).hashCode()
    }

    override fun interact() {
        remove = true
    }


}