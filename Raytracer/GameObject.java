import java.util.*;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
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
        try
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
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Component> void addComponent(Class<T> componentType)
    {
        try {
            // Get the constructor of the class
            Constructor<T> constructor = componentType.getConstructor(GameObject.class);

            // Create a new instance using the constructor
            T instance = constructor.newInstance(this);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    // public <T extends Component> void addComponent(T component)
    // {
    // componentList.add(component);
    // }

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
