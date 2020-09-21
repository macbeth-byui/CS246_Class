package macbeth;

/**
 * The Klingon is-a FlyingObject so it must implement the draw function.
 * THe Klingon is-a Attackable, Cloakable, Radioative (these are interfaces) so they
 * must implement the funtions in those interfaces.
 */
public class Klingon extends FlyingObject implements Attackable, Cloakable, Radioactive {


    @Override
    public void attack() {

    }

    @Override
    public void cloakOn() {

    }

    @Override
    public void cloakOff() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void radiate() {

    }
}
