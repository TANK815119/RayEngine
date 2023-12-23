import java.util.ArrayList;

/**
 * The scene will just hold a list of all the parent transforms for now
 * through this class, all gameObjects will be accesible
 */
public class Scene
{
    private ArrayList<Transform> rootTransformList;
    public Scene(ArrayList<Transform> transList)
    {
        rootTransformList = transList;
    }
    public Scene()
    {
        this(new ArrayList<Transform>());
    }
    
    // root transform list getter, setter
    public void setRootTransformList(ArrayList<Transform> transList)
    {
        rootTransformList = transList;
    }
    public ArrayList<Transform> getRootTransformList()
    {
        return rootTransformList;
    }
    
    // individual gameObject getter, setter
    public void addRootGameObject(GameObject gameObject)
    {
        rootTransformList.add(gameObject.transform());
    }
    // simple linear search to fetch specific game objects by name
    // im not sure how neccessary this accesor is
    public GameObject getRootGameObject(String name) throws Exception
    {
        GameObject rootObject = null;
        for(int i = 0; i < rootTransformList.size(); i++)
        {
            if(rootTransformList.get(i).gameObject().name().equals(name))
            {
                rootObject = rootTransformList.get(i).gameObject();
                break;
            }
        }
        if(rootObject == null)
        {
            throw new Exception("Root GameObject not found");
        }
        return rootObject;
    }
}
