package com.redsquare.jungletribes.box2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.redsquare.jungletribes.entity.Entity
import com.redsquare.jungletribes.utils.Control

import java.util.ArrayList
import java.util.HashMap

class Box2DWorld {
    var world: World
    private val debugRenderer: Box2DDebugRenderer
    private val entityMap: HashMap<Int, Entity>

    init {
        world = World(Vector2(.0f, .0f), true)
        debugRenderer = Box2DDebugRenderer()
        entityMap = HashMap<Int, Entity>()

        world.setContactListener(object : ContactListener {
            override fun beginContact(contact: Contact) {
                val fixtureA = contact.fixtureA
                val fixtureB = contact.fixtureB

                processCollisions(fixtureA, fixtureB, true)
            }

            override fun endContact(contact: Contact) {
                val fixtureA = contact.fixtureA
                val fixtureB = contact.fixtureB

                processCollisions(fixtureA, fixtureB, false)
            }

            override fun preSolve(contact: Contact, oldManifold: Manifold) {}

            override fun postSolve(contact: Contact, impulse: ContactImpulse) {}

        })
    }

    fun tick(camera: OrthographicCamera, control: Control) {
        if (control.debug)
            debugRenderer.render(world, camera.combined)

        world.step(Gdx.app.graphics.deltaTime, 6, 2)
        world.clearForces()
    }

    fun clearAllBodies() {
        val bodies = Array<Body>()
        world.getBodies(bodies)
        for (b in bodies) {
            world.destroyBody(b)
        }

        entityMap.clear()
    }

    private fun processCollisions(aFixture: Fixture, bFixture: Fixture, begin: Boolean) {
        val entityA = entityMap[aFixture.hashCode()]
        val entityB = entityMap[bFixture.hashCode()]

        if (entityA != null && entityB != null) {
            if (aFixture.isSensor && !bFixture.isSensor) {
                entityB!!.collision(entityA, begin)
            } else if (bFixture.isSensor && !aFixture.isSensor) {
                entityA!!.collision(entityB, begin)
            }
        }
    }

    fun populateEntityMap(entities: ArrayList<Entity>) {
        entityMap.clear()
        for (e in entities) {
            entityMap.put(e.hashcode, e)
        }
    }

    fun addEntityToMap(entity: Entity) {
        entityMap.put(entity.hashcode, entity)
    }

    fun removeEntityToMap(entity: Entity) {
        entityMap.remove(entity.hashcode)
    }

}