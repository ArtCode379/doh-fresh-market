package dohdohs.grocery.dohfreshmarket.data.model
import androidx.annotation.StringRes
import dohdohs.grocery.dohfreshmarket.R
enum class ProductCategory(@field:StringRes val titleRes: Int) {
    FRUIT(R.string.huffn_category_fruit),
    VEGETABLES(R.string.huffn_category_vegetables),
    BAKERY(R.string.huffn_category_bakery),
    DRINKS(R.string.huffn_category_drinks),
    DESSERTS(R.string.huffn_category_desserts),
}
