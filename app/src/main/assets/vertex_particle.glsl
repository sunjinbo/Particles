uniform mat4 u_Matrix;

attribute vec3 a_Position;

void main()
{
    gl_Position = vec4(a_Position, 1.0);
    gl_PointSize = 5.0;
}
