package layout.api.model.product_category

import android.os.Parcelable
import com.example.leadsassessment.api.model.product_category.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryBaseModelItem(
    val categoryName: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val products: List<Product>
): Parcelable