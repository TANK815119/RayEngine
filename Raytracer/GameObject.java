import java.util.*;
import java.util.ArrayList;
/**
 * The Gameobject is essentially storage
 * for all the components that handle
 * the "reality" of the scene
 * it also handles tags which are useful to users
 */
public class GameObject
{
    private Transform transform;
    private ArrayList<Component> componentList;
    private String name;
    private String tag;
    
    public GameObject(String n)
    {
        name = n;
        transform = new Transform(this);
        componentList = new ArrayList();
        componentList.add(transform);
    }
    public GameObject(){this("DefaultGameObject");}
    
    //returns highest-index component of given type
    public <T extends Component> Component getComponent(Class<T> componentType) throws Exception
    {
        Component component = null;
        for (int i = 0; i < componentList.size(); i++)
        {
            if(componentType.isInstance(componentList.get(i)))
            {
                component = componentList.get(i);
                break;
            }
        }
        if(component == null)
        {
            throw new Exception("Compnent not found");
        }
        return component;
    }
    
    //tranform getter,setter
    public Transform transform() { return transform; }
    public void transform(Transform trans) { transform = trans; }
    
    //name getter,setter
    public String name() { return name; }
    public void name(String n) { name = n; }
    
    //name getter,setter
    public String tag() { return tag; }
    public void tag(String t) { tag = t; }
}
