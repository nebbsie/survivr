/* Fragment shader */
uniform sampler2D tex;

uniform float offset;

void main(){

  vec4 color = (offset, 1.0,0.0, offset);

  gl_FragColor = color; 
}
