package com.example.navigation

import com.example.navigation.intent_builders.ActivityIntentBuilder
import com.example.navigation.intent_builders.CaptureCarePhotoIntentBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module

val navigationModule = module {
    factory(named(DestinationType.HOME)) {
        HomeDestination(
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.HOME))
            )
        )
    }
    factory(named(DestinationType.CARE_DETAILS)) {
        CareDetailsDestination(
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.CARE_DETAILS))
            )
        )
    }
    factory(named(DestinationType.PEH_BALANCE)) {
        PehBalanceDestination(
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.PEH_BALANCE))
            )
        )
    }
    factory(named(DestinationType.CARE_PHOTOS_GALLERY)) {
        CarePhotosGalleryDestination(
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.CARE_PHOTOS_GALLERY))
            )
        )
    }
    factory(named(DestinationType.SELECT_PRODUCT)) {
        SelectProductDestination(
            defaultResult = SelectProductResult(selectedProductId = null),
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.SELECT_PRODUCT))
            )
        )
    }
    factory(named(DestinationType.PRODUCT_DETAILS)) {
        ProductDetailsDestination(
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.PRODUCT_DETAILS))
            )
        )
    }
    factory(named(DestinationType.CARE_SCHEMA_DETAILS)) {
        CareSchemaDetailsDestination(
            intentBuilder = ActivityIntentBuilder(
                activityClass = get(named(DestinationType.CARE_SCHEMA_DETAILS))
            )
        )
    }
    factory(named(DestinationType.CAPTURE_CARE_PHOTO)) {
        CaptureCarePhotoDestination(
            defaultResult = CaptureCarePhotoResult(photoData = null),
            intentBuilder = CaptureCarePhotoIntentBuilder()
        )
    }
}