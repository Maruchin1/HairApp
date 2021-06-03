package com.example.navigation

import android.os.Parcelable
import com.example.corev2.entities.Product
import com.example.navigation.base.Destination
import com.example.navigation.base.DestinationWithParams
import com.example.navigation.base.DestinationWithParamsAndResult
import com.example.navigation.base.DestinationWithResult
import kotlinx.parcelize.Parcelize

const val EXTRA_DESTINATION_PARAMS = "extra_destination_params"
const val EXTRA_DESTINATION_RESULT = "extra_destination_result"

typealias HomeDestination = Destination

typealias CareDetailsDestination = DestinationWithParams<CareDetailsParams>

@Parcelize
data class CareDetailsParams(val careId: Long) : Parcelable

typealias PehBalanceDestination = DestinationWithParams<PehBalanceParams>

@Parcelize
data class PehBalanceParams(val careId: Long) : Parcelable

typealias CarePhotosGalleryDestination = DestinationWithParams<CarePhotosGalleryParams>

@Parcelize
data class CarePhotosGalleryParams(val careId: Long) : Parcelable

typealias SelectProductDestination = DestinationWithParamsAndResult<SelectProductParams, SelectProductResult>

@Parcelize
data class SelectProductParams(val productType: Product.Type?) : Parcelable

@Parcelize
data class SelectProductResult(val selectedProductId: Long?) : Parcelable

typealias ProductDetailsDestination = DestinationWithParams<ProductDetailsParams>

@Parcelize
data class ProductDetailsParams(val productId: Long) : Parcelable

typealias CareSchemaDetailsDestination = DestinationWithParams<CareSchemaDetailsParams>

@Parcelize
data class CareSchemaDetailsParams(val careSchemaId: Long) : Parcelable

typealias CaptureCarePhotoDestination = DestinationWithResult<CaptureCarePhotoResult>

@Parcelize
data class CaptureCarePhotoResult(val photoData: String?) : Parcelable