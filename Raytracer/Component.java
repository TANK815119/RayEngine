
/**
 * Write a description of interface Component here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Component
{
    private GameObject gameObject;
    public Component(GameObject gameObject)
    {
        this.gameObject = gameObject;
    }
    public GameObject gameObject(){return gameObject;}
    //Note: no gameObject mutator because components won't ever be dissconected from their game object
}
