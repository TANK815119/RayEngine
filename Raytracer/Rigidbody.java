
/**
 * The Rigidbody will handle the physics data ofa gven oject
 * it will also handle forces applied to that object
 * it is a component
 */
public class Rigidbody extends Component
{
    private Transform transform;
    public Rigidbody(GameObject gameObject)
    {
        super(gameObject);
        transform = gameObject.transform();
    }
}
