package dohdohs.grocery.dohfreshmarket.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dohdohs.grocery.dohfreshmarket.data.dao.CartItemDao
import dohdohs.grocery.dohfreshmarket.data.dao.OrderDao
import dohdohs.grocery.dohfreshmarket.data.database.converter.Converters
import dohdohs.grocery.dohfreshmarket.data.entity.CartItemEntity
import dohdohs.grocery.dohfreshmarket.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HUFFNDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}