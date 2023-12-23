
/**
 * the face data for a CubeMesh
 */
public class CubeMesh extends Mesh
{
    public CubeMesh(GameObject gameObject)
    {
        super(gameObject, null);
        //put data for Cube Mesh here
        //doing this manually will allow me to wrap my head around the OBJReader better
        Face[] faceArr = new Face[6];
        Vector3[] pointArr = new Vector3[4];
        Vector3 normal;
        
        pointArr[0] = new Vector3(1f, 1f, 1f);
        pointArr[1] = new Vector3(1f, 1f, 1f);
        pointArr[2] = new Vector3(1f, 1f, 1f);
        pointArr[3] = new Vector3(1f, 1f, 1f);
        normal = new Vector3(0f, 0f, 0f);
        faceArr[0] = new Face(pointArr, normal);
        
        pointArr[0] = new Vector3(1f, 1f, 1f);
        pointArr[1] = new Vector3(1f, 1f, 1f);
        pointArr[2] = new Vector3(1f, 1f, 1f);
        pointArr[3] = new Vector3(1f, 1f, 1f);
        normal = new Vector3(0f, 0f, 0f);
        faceArr[0] = new Face(pointArr, normal);
        
        pointArr[0] = new Vector3(1f, 1f, 1f);
        pointArr[1] = new Vector3(1f, 1f, 1f);
        pointArr[2] = new Vector3(1f, 1f, 1f);
        pointArr[3] = new Vector3(1f, 1f, 1f);
        normal = new Vector3(0f, 0f, 0f);
        faceArr[0] = new Face(pointArr, normal);
        
        pointArr[0] = new Vector3(1f, 1f, 1f);
        pointArr[1] = new Vector3(1f, 1f, 1f);
        pointArr[2] = new Vector3(1f, 1f, 1f);
        pointArr[3] = new Vector3(1f, 1f, 1f);
        normal = new Vector3(0f, 0f, 0f);
        faceArr[0] = new Face(pointArr, normal);
        
        pointArr[0] = new Vector3(1f, 1f, 1f);
        pointArr[1] = new Vector3(1f, 1f, 1f);
        pointArr[2] = new Vector3(1f, 1f, 1f);
        pointArr[3] = new Vector3(1f, 1f, 1f);
        normal = new Vector3(0f, 0f, 0f);
        faceArr[0] = new Face(pointArr, normal);
        
        this.setFaces(faceArr); //then put that Face[] data for the CubeMesh in here
    }
}
