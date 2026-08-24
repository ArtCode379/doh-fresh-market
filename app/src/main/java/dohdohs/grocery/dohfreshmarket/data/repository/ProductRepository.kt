package dohdohs.grocery.dohfreshmarket.data.repository
import dohdohs.grocery.dohfreshmarket.data.model.Product
import dohdohs.grocery.dohfreshmarket.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
class ProductRepository {
    private val products = listOf(
        product(1, "Sunrise Strawberries", "Sweet British strawberries selected at peak ripeness.", ProductCategory.FRUIT, 4.50, "1464965911861-746a04b4bca6"),
        product(2, "Hass Avocados", "Creamy ripe avocados with a rich, buttery texture.", ProductCategory.FRUIT, 3.80, "1523049673857-eb18f1d7b578"),
        product(3, "Heritage Tomatoes", "Colourful, juicy tomatoes full of natural sweetness.", ProductCategory.VEGETABLES, 4.20, "1592924357228-91a4daadcfea"),
        product(4, "Garden Vegetable Box", "A seasonal mix of crisp vegetables for everyday cooking.", ProductCategory.VEGETABLES, 12.00, "1540420773420-3366772f4999"),
        product(5, "Sourdough Country Loaf", "Slow-fermented sourdough with a caramelised crust and airy crumb.", ProductCategory.BAKERY, 5.25, "1509440159596-0249088772ff"),
        product(6, "Butter Croissant Pair", "Two flaky croissants made with cultured butter.", ProductCategory.BAKERY, 4.75, "1555507036-ab1f4038808a"),
        product(7, "Cold-Pressed Orange Juice", "Bright orange juice with no added sugar or concentrate.", ProductCategory.DRINKS, 3.95, "1600271886742-f049cd451bba"),
        product(8, "Berry Kombucha", "Fermented tea with mixed berries and a crisp sparkle.", ProductCategory.DRINKS, 3.50, "1595981267035-7b04ca84a82d"),
        product(9, "Lemon Curd Tart", "Crisp pastry with silky citrus filling and fresh zest.", ProductCategory.DESSERTS, 5.95, "1519915028121-7d3463d20b13"),
        product(10, "Chocolate Berry Slice", "Chocolate sponge layered with smooth cream and fresh berries.", ProductCategory.DESSERTS, 6.25, "1578985545062-69928b1d9587"),
        product(11, "Crisp Orchard Apples", "Crunchy seasonal apples with a sweet-tart flavour.", ProductCategory.FRUIT, 3.20, "1560806887-1e4cd0b6cbd6"),
        product(12, "Cinnamon Morning Buns", "Soft spiral buns baked with brown sugar and cinnamon.", ProductCategory.BAKERY, 5.50, "1509365465985-25d11c17e812"),
    )
    fun observeById(id: Int): Flow<Product?> = flowOf(products.find { it.id == id })
    fun getById(id: Int): Product? = products.find { it.id == id }
    fun observeAll(): Flow<List<Product>> = flowOf(products)

    private fun product(
        id: Int,
        title: String,
        description: String,
        category: ProductCategory,
        price: Double,
        photoId: String,
    ): Product {
        return Product(
            id = id,
            title = title,
            description = description,
            category = category,
            price = price,
            imageUrl = "https://images.unsplash.com/photo-$photoId?w=1200",
        )
    }
}
