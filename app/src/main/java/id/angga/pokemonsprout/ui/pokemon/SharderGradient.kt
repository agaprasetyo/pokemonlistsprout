package id.angga.pokemonsprout.ui.pokemon

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import org.intellij.lang.annotations.Language

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.shaderGradientBackground(
    startColor: Color,
    endColor: Color,
) = this.drawWithCache {
    val shader = RuntimeShader(AngledShader)

    shader.setFloatUniform("resolution", size.width, size.height)
    shader.setColorUniform("startColor", startColor.toArgb())
    shader.setColorUniform("endColor", endColor.toArgb())

    val gradientBrush = ShaderBrush(shader)
    onDrawBehind {
        drawRect(
            brush = gradientBrush,
            size = size
        )
    }
}

@Language("AGSL")
val AngledShader = """
    uniform float2 resolution;
    layout(color) uniform half4 startColor;
    layout(color) uniform half4 endColor;

    vec4 main(vec2 fragCoord) {
        vec2 uv = fragCoord / resolution.xy;
        
        float angle = radians(15.0);
        vec2 direction = vec2(cos(angle), sin(angle));
        
        // Calculate the gradient
        float gradient = dot(uv, direction);
        
        // Interpolate between the colors
        return mix(startColor, endColor, gradient);
    }
""".trimIndent()