package be.mauricecantaert.mobileappdevandroid.model

import android.content.Context
import be.mauricecantaert.mobileappdevandroid.R

enum class ErrorType {
    DEVICE_OFFLINE,
    EXCEPTION,
}

fun ErrorType.getMessage(context: Context): String {
    return when (this) {
        ErrorType.DEVICE_OFFLINE -> context.getString(R.string.errorMessage_offline)
        ErrorType.EXCEPTION -> context.getString(R.string.errorMessage_exception)
    }
}
