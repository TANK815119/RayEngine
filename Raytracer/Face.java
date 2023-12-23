
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
    public Face(Vector3[] pointArr, Vector3 normal)
    {
        this.pointArr = pointArr;
        this.normal = normal;
    }
    
    //no getter and setter because when would i actually do that?
}
