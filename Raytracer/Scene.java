import java.util.ArrayList;
import java.lang.reflect.Constructor;

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
        gameObject.transform().setScene(this);
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

    //recursive linear tree search for all Tree
    private ArrayList<Transform> getTransformList()
    {
        ArrayList<Transform> transList = new ArrayList<Transform>();
        for(int i = 0; i < rootTransformList.size(); i++)
        {
            Transform rootTrans = rootTransformList.get(i);
            transList.add(rootTrans);
            transList.addAll(getChildren(rootTrans));
        }
        return transList;
    }

    private static ArrayList<Transform> getChildren(Transform parent)
    {
        ArrayList<Transform> transList = new ArrayList<Transform>();
        for(int i = 0; i < parent.numberOfChildren(); i++)
        {
            Transform childTrans = parent.getChild(i);
            transList.add(childTrans);
            transList.addAll(getChildren(childTrans));
        }
        return transList;
    }

    //recursive linear tree search for all gameObjects with a certain component
    public <T extends Component> ArrayList<GameObject> getGameObjectList(Class<T> componentType)
    {
        ArrayList<GameObject> objectList = new ArrayList<GameObject>();

        ArrayList<Transform> transList = this.getTransformList();
        for(int i = 0; i < transList.size(); i++)
        {
            GameObject thisObject = transList.get(i).gameObject();
            if(thisObject.hasComponent(componentType))
            {
                objectList.add(thisObject);
            }
        }

        return objectList;
    }

    //yoink all the faces of the objects with a mesh component in this scene
    // VERY UNPERFORMANT
    public Face[] getGeometry()
    {
        ArrayList<GameObject> objectList = this.getGameObjectList(Mesh.class);

        //nab the number of faces for the array
        int numFaces = 0;
        for(int i = 0; i < objectList.size(); i++)
        {
            numFaces += objectList.get(i).getComponent(Mesh.class).getFaces().length; // hella unperformant; @TODO FIX THAT
        }
        Face[] faces = new Face[numFaces];

        //nab the actual faces into one arrray
        int faceIndex = 0;
        for(int  i = 0; i < objectList.size(); i++)
        {
            Face[] meshFaces = objectList.get(i).getComponent(Mesh.class).getFaces();
            for(int j = 0; j < meshFaces.length; j++)
            {
                faces[faceIndex] = meshFaces[j];
                faceIndex++;
            }
        }

        return faces;
    }
}
