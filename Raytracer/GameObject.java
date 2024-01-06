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
    private Scene scene;

    public GameObject(String n)
    {
        name = n;
        transform = new Transform(this);
        componentList = new ArrayList();
        componentList.add(transform);
    }

    public GameObject(){this("DefaultGameObject");}

    //returns lowest-index component of given type
    public <T extends Component> T getComponent(Class<T> componentType)
    {
        try
        {
            T component = null;
            for (int i = 0; i < componentList.size(); i++)
            {
                if(componentType.isInstance(componentList.get(i)))
                {
                    component = (T)componentList.get(i);
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

    //checks if this GameObejct has a certain component
    public <T extends Component> boolean hasComponent(Class<T> componentType)
    {
        for (int i = 0; i < componentList.size(); i++)
        {
            if(componentType.isInstance(componentList.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    //adds component of class type
    // i.e. ExampleGameObject.addComponent(Mesh.class)
    public <T extends Component> void addComponent(Class<T> componentType)
    {
        try {
            // Get the constructor of the class
            //the "GameObject.class" specifies which the specific constructor to be used
            //in this case, it specifies the constructor must have a GameObject gameObject parameter
            Constructor<T> constructor = componentType.getConstructor(GameObject.class); // there was a parameter "GameObject.class" here

            // Create a new instance using the constructor
            T component = constructor.newInstance(this);

            //add the created component to the arraylist
            componentList.add(component);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    //a class like this one is useful for more rhobust component adding
    //you can use it to first make a component with its constructor and
    //all the parameters, and then use this method to properly add it to the
    //component arraylist
    public <T extends Component> void addComponent(T component)
    {
        componentList.add(component);
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
