
/**
 * The Face class contains all the neccessary information of a "face" of a polgyon
 * It has an array that stores the Vector3 points that make up the face
 * It also contains the information for the normal of the face, which is also stored in Vector3
 * The normal data helps reflect arrays properly in the raycaster
 * all of this information is then used to calculate whther a ray interesects with it
 * and then bounce it
 * 
 * Vertext Textures will be necessary for image textures, but ill save that for later
 */
public class Face
{
    private Vector3[] pointArr;
    private Vector3 normal;
    private Vector3 edge1; //not used right now
    private Vector3 edge2; //not used right now
    
    public Face(Vector3[] pointArr, Vector3 normal)
    {
        System.out.println(normal);
        this.pointArr = pointArr;
        this.normal = normal;
    }
    
    public Vector3 getPoint0()
    {
        return pointArr[0];
    }
    
    //the edge vector2
    public Vector3 getEdge1()
    {
        //System.out.println(pointArr[1] + " subtract " + pointArr[0]);
        return pointArr[1].subtract(pointArr[0]);
    }
    public Vector3 getEdge2()
    {
        //System.out.println(pointArr[2] + " subtract " + pointArr[0]);
        return pointArr[2].subtract(pointArr[0]);
    }
    
    //the normal vector
    public Vector3 getNormal()
    {
        Vector3 crossProd = this.getEdge1().crossProduct(this.getEdge2());
        crossProd.normalize();
        //System.out.println(this.getEdge1() + " cross prod " + this.getEdge2());
        
        //test code #TODO remove
        if(crossProd.x != normal.x || crossProd.y != normal.y || crossProd.z != normal.z)
        {
            //System.out.println(normal + " does not match calc normal of " + crossProd);
        }
        else
        {
            //System.out.println("match");
        }
        
        return crossProd;
    }
    
    public String toString()
    {
        String output = "";
        output += "verticies: " + pointArr[0] + " " + pointArr[1] + " " + pointArr[2];
        output += "\n" + "normal: " + normal;
        return output;
    }
}
