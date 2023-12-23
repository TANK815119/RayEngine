/**
 * Mesh contains the information necessary to contruct
 * a visible mesh through points that makeup traingles and normals
 * verticies are packaged with normals in the Face class
 */
public class Mesh extends Component
{
    private Face[] faceArr;
    public Mesh(GameObject gameObject, Face[] pointArray)
    {
        super(gameObject);
        this.faceArr = faceArr;
    }
    public Mesh(GameObject gameObject)
    {
        this(gameObject, null);
    }
    
    // face array getter, settr
    public void setFaces(Face[] pointArray)
    {
        this.faceArr = faceArr;
    }
    public Face[] getFaces()
    {
        return faceArr;
    }
}
