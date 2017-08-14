package com.redsquare.jungletribes.box2d

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.*

object Box2DHelper {

    fun createBody(world: World, width: Float, height: Float, xOffset: Float, yOffset: Float, pos: Vector3, type: BodyDef.BodyType): Body {
        val body: Body
        val bodyDef = BodyDef()
        bodyDef.position.set(pos.x + width / 2 + xOffset, pos.y + height / 2 + yOffset)
        bodyDef.angle = 0f
        bodyDef.fixedRotation = true
        bodyDef.type = type
        body = world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        val boxShape = PolygonShape()
        boxShape.setAsBox(width / 2, height / 2)

        fixtureDef.shape = boxShape
        fixtureDef.restitution = 0.4f

        body.createFixture(fixtureDef)
        boxShape.dispose()

        return body
    }

    fun createSensor(world: World, width: Float, height: Float, xOffset: Float, yOffset: Float, pos: Vector3, type: BodyDef.BodyType): Body {
        val body: Body
        val bodyDef = BodyDef()
        bodyDef.position.x = pos.x + xOffset
        bodyDef.position.y = pos.y + yOffset
        bodyDef.angle = 0f
        bodyDef.fixedRotation = true
        bodyDef.type = type
        body = world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        val boxShape = PolygonShape()
        boxShape.setAsBox(width / 2, height / 2)

        fixtureDef.shape = boxShape
        fixtureDef.isSensor = true

        body.createFixture(fixtureDef)
        boxShape.dispose()

        return body
    }

}
