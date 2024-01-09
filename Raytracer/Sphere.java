
/**
 * essentially just stores the radius of the sphere
 */
public class Sphere extends Component
{
    private float radius;
    public Sphere(GameObject gameObject, float r)
    {
        super(gameObject);
        radius = r;
    }
    public Sphere(GameObject gameObject)
    {
        this(gameObject, 1f);
    }
    
    //getter, setter
    public void setRadius(float r)
    {
        radius = r;
    }
    public float getRadius()
    {
        return radius;
    }
}
