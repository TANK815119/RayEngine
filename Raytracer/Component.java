
/**
 * Component is an abstract that defines that all components
 * must have an immutable connection to their gameObject
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
