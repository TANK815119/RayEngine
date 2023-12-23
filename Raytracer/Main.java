
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
    public static void main(String args[])
    {
        Scene scene = new Scene();
        GameObject gameObject = new GameObject("My Object");
        scene.addRootGameObject(gameObject);
        gameObject.addComponent(CubeMesh.class);
    }
}
