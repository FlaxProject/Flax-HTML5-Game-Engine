package ie.flax.flaxengine.client;

import java.util.HashMap;

/**
 * This class handles all the real world physics such as forces been applied to bodys,
 * collision with bodies etc. Entities registered with this class will be taken into account
 * when all physical caluations are done.
 * @author Ciarán McCann
 *
 */
public class Physics {
	
	/**
	 * Holds references to all the objects who want to be included in the physical world caluations,
	 * eg: movements, forces, colllisions. 
	 */
	private static HashMap<String, FEntity> physicalWorld = new HashMap<String, FEntity>();
	
	
	/**
	 * Pass the this pionter to this method call and that object will REMOVED from the
	 * physical world. In that it will no longer registered for all physcial responese.
	 * Eg: Forces applied, collisions, etc
	 * @param objectTobeRemoved
	 */
	public static void unregisterForPhysics(FEntity objectTobeRemoved)
	{
		physicalWorld.remove(objectTobeRemoved);
	}
	
	/**
	 * Pass the this pionter to this method call and that object will become part
	 * of the physical world. In that it will be registered for all physcial responese.
	 * Eg: Forces applied, collisions, etc
	 * @param objectToBeRegistered
	 */
	public static void registerForPhysics(FEntity objectToBeRegistered)
	{
		physicalWorld.put(objectToBeRegistered.toString(), objectToBeRegistered);
	}

	/**
	 * Updates all the physics objects of the Entites in the world
	 * @param timePassed
	 */
	public void upadate(double timePassed)
	{
		//TODO: calucate velocity and direction and find new postition
		//TODO: Caluate forces acting on object and find new position
		
		
	}

}
