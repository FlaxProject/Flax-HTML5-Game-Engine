package ie.flax.flaxengine.client.Graphic;

/**
 * This is necessary for the requestAnimationFrame loop, it makes it easier to callback from the js.
 * Wouldn't worry about it right now, just use a timer.
 * @author elangca
 *
 */
public interface TimerCallback {
    public void fire();
}
