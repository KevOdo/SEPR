package com.deadlast.world;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

/**
 * Converts map objects into Box2d objects.
 * Inspiration from
 * https://gamedev.stackexchange.com/questions/66924/how-can-i-convert-a-tilemap-to-a-box2d-world
 * with small modifications
 * 
 * @author daemonaka (https://gamedev.stackexchange.com/users/41604/daemonaka)
 * @author Xzytl
 *
 */
public class MapBodyBuilder {

	/**
	 * The number of pixels per tile in the map
	 */
	private static float PPT = 32;

	public static Array<Body> buildBodies(TiledMap map, World world) {
		MapObjects objs = map.getLayers().get("Walls").getObjects();

		Array<Body> bodies = new Array<>();

		objs.forEach(o -> {
			if (o instanceof TextureMapObject)
				return;

			Shape shape;

			if (o instanceof RectangleMapObject) {
				shape = getRectangle((RectangleMapObject) o);
			} else if (o instanceof PolygonMapObject) {
				shape = getPolygon((PolygonMapObject) o);
			} else if (o instanceof PolylineMapObject) {
				shape = getPolyline((PolylineMapObject) o);
			} else if (o instanceof CircleMapObject) {
				shape = getCircle((CircleMapObject) o);
			} else {
				return;
			}

			BodyDef bDef = new BodyDef();
			bDef.type = BodyType.StaticBody;
			Body body = world.createBody(bDef);
			body.createFixture(shape, 1);

			bodies.add(body);
			shape.dispose();
		});

		return bodies;
	}

	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / PPT,
				(rectangle.y + rectangle.height * 0.5f) / PPT);
		polygon.setAsBox(rectangle.width * 0.5f / PPT, rectangle.height * 0.5f / PPT, size, 0.0f);
		return polygon;
	}

	private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
		PolygonShape polygon = new PolygonShape();
		float[] vertices = polygonObject.getPolygon().getTransformedVertices();

		float[] worldVertices = new float[vertices.length];

		for (int i = 0; i < vertices.length; ++i) {
			worldVertices[i] = vertices[i] / PPT;
		}

		polygon.set(worldVertices);
		return polygon;
	}

	private static ChainShape getPolyline(PolylineMapObject polylineObject) {
		float[] vertices = polylineObject.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for (int i = 0; i < vertices.length / 2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i * 2] / PPT;
			worldVertices[i].y = vertices[i * 2 + 1] / PPT;
		}

		ChainShape chain = new ChainShape();
		chain.createChain(worldVertices);
		return chain;
	}

	private static CircleShape getCircle(CircleMapObject circleObject) {
		Circle circle = circleObject.getCircle();
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(circle.radius / PPT);
		circleShape.setPosition(new Vector2(circle.x / PPT, circle.y / PPT));
		return circleShape;
	}

}
