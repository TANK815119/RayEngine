
/**
 * Write a description of class TriMesh here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TriMesh extends Mesh
{
    public TriMesh(GameObject gameObject)
    {
        super(gameObject, null);
        
        Face[] faceArr = new Face[1];
        // triangles ordered counter clockwise
        // both riangles will have no depth, so all zeroes on the x axisd
        // z is horizontal and y is verticle
        
        // make trianngle 1 bottom left
        Vector3[] pointArr = new Vector3[]
        {
            new Vector3(0, 0.5f, 0.0f), //middle top
            new Vector3(0, -0.5f, -0.5f), //bottom left
            new Vector3(0, -0.5f, 0.5f) //bottom right
        };
        Vector3 normal = new Vector3(-1f, 0, 0); //normal is looking at camera which is on thye negative x axis
        faceArr[0] = new Face(pointArr, normal);
        
        this.setFaces(faceArr);
        
        //print out all the created data
        for(int i = 0; i < faceArr.length; i++)
        {
            System.out.println("TRIANGLE " + i);
            System.out.println(faceArr[i]);
            System.out.println(faceArr[i].getNormal());
        }
    }
}
