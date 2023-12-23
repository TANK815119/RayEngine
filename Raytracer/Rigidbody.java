
/**
 * Write a description of class Rigidbody here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
