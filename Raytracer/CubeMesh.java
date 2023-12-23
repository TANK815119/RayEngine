
/**
 * the face data for a CubeMesh
 * it will be made of triangles like all meshes
 * in this engine
 */
public class CubeMesh extends Mesh
{
    public CubeMesh(GameObject gameObject)
    {
        super(gameObject, null);
        //put data for Cube Mesh here
        //doing this manually will allow me to wrap my head around the OBJReader better
        Face[] faceArr = new Face[12];
        Vector3[] normalArr = new Vector3[]{
            new Vector3(1f, 0f, 0f),
            new Vector3(0f, 1f, 0f),
            new Vector3(0f, 0f, 1f),
            new Vector3(-1f, 0f, 0f),
            new Vector3(0f, -1f, 0f),
            new Vector3(0f, 0f, -1f),
        };
        
        //use the normals of each face to generate said faces
        int faceIndex = 0;
        for(int i = 0; i < normalArr.length; i++)
        {
            //make an array of the 4 vectors that make up the side
            Vector3[] squareArr = new Vector3[4];
            int squareIndex = 0;
            
             // loop though the normal Vector3
            for(int j = 0; j < 3; j++)
            {
                 // if the z, y, or z is not the normal direction
                if(normalArr[i].get(j) == 0)
                {
                    //make a point that has a pso and neg of the unnasigned coordinate
                    squareArr[squareIndex] = normalArr[i];
                    squareArr[squareIndex].set(j, 1f);
                    squareIndex++;
                    squareArr[squareIndex] = normalArr[i];
                    squareArr[squareIndex].set(j, -1f);
                    squareIndex++;
                }
            }
            
            //loop through and assign any zeros to 1, resulting in 4 variants
            for(int j = 0; j < squareArr.length; j++)
            {
                for(int k = 0; k < 3; k++)
                {
                    if(squareArr[j].get(k) == 0)
                    {
                        squareArr[j].set(k, 1f);
                    }
                }
            }
            
            //the 1st and 4th points are opposite
            //so are are the 2nd and 3rd
            //ill use the 1st and 4th as the basis of the two triangles
            
            //generate triangle one
            Vector3[] triArr = new Vector3[3];
            int triIndex = 0;
            for(int j = 0; j < squareArr.length; j++)
            {
                if(j != 3)
                {
                     // yoink the value of the square into the triangle
                    triArr[triIndex] = squareArr[j];
                    triIndex++;
                }
            }
            faceArr[faceIndex] = new Face(triArr, normalArr[i]);
            faceIndex++;
            
            //generate triangle two
            triArr = new Vector3[3];
            triIndex = 0;
            for(int j = 0; j < squareArr.length; j++)
            {
                if(j != 0)
                {
                     // yoink the value of the square into the triangle
                    triArr[triIndex] = squareArr[j];
                    triIndex++;
                }
            }
            faceArr[faceIndex] = new Face(triArr, normalArr[i]);
            faceIndex++;
        }
        
        //then put that Face[] data for the CubeMesh in here
        this.setFaces(faceArr);
    }
}
