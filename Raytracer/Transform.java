import java.util.ArrayList;
/**
 * The transform component is where the object is
 * It includes positional and rotation data
 * but it also includes where the gameObject is relative
 * to other gameObjects in the heirarchy
 */
public class Transform extends Component
{
    //spacial variables
    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale; // to be implemented latter
    
    //hierarchical variables
    private Transform parent;
    private ArrayList<Transform> childList;
    private Scene scene;
    
    public Transform(GameObject gameObject, Vector3 pos, Vector3 rot)
    {
        super(gameObject);
        position = pos;
        rotation = rot;
        childList = new ArrayList<Transform>();
    }
    public Transform(GameObject gameObject)
    {
        this(gameObject, new Vector3(), new Vector3());
    }
    
    //positional getter,sette
    public Vector3 position() { return position; }
    public void position(Vector3 pos) { position = pos; }
    public void position(float x, float y, float z) { position = new Vector3(x, y, z); }
    public void translate(float dx, float dy, float dz)
    {
        position.x += dx;
        position.y += dy;
        position.z += dz;
    }
    
    //rotation getter,setter
    public Vector3 rotation() { return rotation; }
    public void rotation(Vector3 rot) { rotation = rot; }
    public void rotation(float x, float y, float z) { rotation = new Vector3(x, y, z); }
    public void rotate(float dx, float dy, float dz)
    {
        rotation.x += dx;
        rotation.y += dy;
        rotation.z += dz;
    }
    
    //parent getter,setter
    public Transform parent() { return parent; }
    public void parent(Transform par)
    {
        parent = par;
        parent.addChild(this);
    }
    
    //children getter,setter
    public Transform getChild(int index) { return childList.get(index); }
    public void addChild(Transform child) { childList.add(child);
                                            child.parent(this); }
    public Transform removeChild(int index)
    {
        Transform temp = childList.remove(index);
        temp.parent(null);
        return temp;
    }
    public int numberOfChildren() { return childList.size(); }
    
    //scene getter,setter
    public Scene getScene() { return scene; }

    public void setScene(Scene s) { scene = s; }
}
