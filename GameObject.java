import java.util.*;
import java.util.ArrayList;
/**
 * Write a description of class GameObject here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameObject
{
    private Transform transform;
    private ArrayList<Component> componentList;
    private String name;
    
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
    
}
