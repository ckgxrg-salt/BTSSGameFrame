#version 400 core
in vec2 texCoord;

out vec4 FragColor;

uniform sampler2D tex;

void main(){
    FragColor = texture(tex, texCoord);
    if(FragColor.a < 0.1){
        discard;
    }
}