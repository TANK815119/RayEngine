
/**
 * Write a description of class Transform here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Transform implements Component
{
    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale; // to be implemented latter
    public Transform(Vector3 pos, Vector3 rot)
    {
        position = pos;
        rotation = rot;
    }
    public Transform()
    {
        this(new Vector3(), new Vector3());
    }
}
