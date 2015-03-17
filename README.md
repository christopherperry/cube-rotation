# cube-rotation
Rotating a Cube relative to World coordinates in OpenGL

Vertex/Fragment shaders are in `res.raw`

`CubeSurfaceView` contains the renderer that renders the cube. This class also handles the touch events for manipulating the cube.

`CubeRenderer` creates the shader program and draws the cube. `onDrawFrame()` is where the draw happens.
