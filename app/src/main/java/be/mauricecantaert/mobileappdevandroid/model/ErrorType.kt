package be.mauricecantaert.mobileappdevandroid.model

import android.content.Context
import be.mauricecantaert.mobileappdevandroid.R
import be.mauricecantaert.mobileappdevandroid.model.ErrorType.DEVICE_OFFLINE
import be.mauricecantaert.mobileappdevandroid.model.ErrorType.EXCEPTION

/**
 * Enum class representing different types of errors thrown by a ViewModel.
 * [DEVICE_OFFLINE]: Indicates an error due to the device being offline.
 * [EXCEPTION]: Indicates a general exception error.
 */
enum class ErrorType {
    DEVICE_OFFLINE,
    EXCEPTION,
}

/**
 * Retrieves the corresponding error message for the specific [ErrorType].
 * @param context The context used to retrieve the error message resources.
 * @return The error message associated with the [ErrorType].
 */
fun ErrorType.getMessage(context: Context): String {
    return when (this) {
        DEVICE_OFFLINE -> context.getString(R.string.errorMessage_offline)
        EXCEPTION -> context.getString(R.string.errorMessage_exception)
    }
}
