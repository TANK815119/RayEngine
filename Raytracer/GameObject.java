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
    public GameObject(Transform trans)
    {
        transform = trans;
    }
    public GameObject()
    {
        this(new Transform());
    }
}
