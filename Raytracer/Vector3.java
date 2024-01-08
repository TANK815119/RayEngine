
/**
 * The Vector3 as written here will act as a
 * simple data type that will be used throught
 * the project in multiple different ways
 */
public class Vector3
{
    public float x;
    public float y;
    public float z;
    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3(Vector3 other)
    {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector3()
    {
        this(0, 0, 0);
    }

    // getter, setter that make the Vecotr3 act like an array
    public float get(int i)
    {
        try
        {
            switch(i)
            {
                case 0: return x;
                case 1: return y;
                case 2: return z;
                default: throw new Exception("Vector3 out of bounds");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    public void set(int i, float value)
    {
        try
        {
            switch(i)
            {
                case 0: x = value; break;
                case 1: y = value; break;
                case 2: z = value; break;
                default: throw new Exception("Vector3 out of bounds");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String toString()
    {
        String output = "";
        output += "(" + x + ", " + y + ", " + z + ")";
        return output;
    }
    
    //matrix math
    public Vector3 add(Vector3 other)
    {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    public Vector3 subtract(Vector3 other)
    {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }
    public Vector3 multiply(Vector3 other)
    {
        return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
    }
    public Vector3 divide(Vector3 other)
    {
        return new Vector3(this.x / other.x, this.y / other.y, this.z / other.z);
    }
    public float dotProduct(Vector3 other)
    {
        return this.x*other.y + this.y*other.y + this.z*other.z;
    }
    public Vector3 crossProduct(Vector3 other)
    {
        float newX = this.y * other.z - this.z * other.y;
        float newY = this.x * other.z - this.z * other.x;
        float newZ = this.x * other.y - this.y * other.x;
        return new Vector3(newX, newY, newZ);
    }
    public void normalize()
    {
        float lengthSquared = x * x + y * y + z * z;

        if (lengthSquared == 0) {
            return; // Vector is already normalized or has zero length
        }

        float invLength = 1 / (float) Math.sqrt(lengthSquared);
        x *= invLength;
        y *= invLength;
        z *= invLength;
    }
}
