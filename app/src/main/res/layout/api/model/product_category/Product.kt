package layout.api.model.product_category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val description: String,
    val id: Int,
    val imageURL: String,
    val name: String,
    val price: Double
): Parcelable