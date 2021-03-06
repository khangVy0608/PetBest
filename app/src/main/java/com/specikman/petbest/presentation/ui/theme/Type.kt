package ark.coding.grocery.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.specikman.petbest.R


val nunitoFamily = FontFamily(
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_regular, FontWeight.Medium),
    Font(R.font.nunito_bold, FontWeight.Bold)
)

val railwayFamily = FontFamily(
    Font(R.font.raleway_regular, FontWeight.Normal)
)

val Typography = Typography(
    defaultFontFamily = nunitoFamily
)