package com.wtduyuwnt.overrecipes.data.remote

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

fun JsonElement.objectOrNull(): JsonObject? = this as? JsonObject

fun JsonElement.arrayOrNull(): JsonArray? = this as? JsonArray

fun JsonElement.asArrayOrFirstNested(): List<JsonElement> = when (this) {
    is JsonArray -> this
    is JsonObject -> values.firstNotNullOfOrNull { it as? JsonArray }.orEmpty()
    else -> emptyList()
}

fun JsonObject.string(vararg keys: String): String? = keys.firstNotNullOfOrNull { key ->
    (this[key] as? JsonPrimitive)?.takeIf { it.isString || it.intOrNull != null }?.content
}?.takeIf { it.isNotBlank() }

fun JsonObject.int(vararg keys: String): Int? = keys.firstNotNullOfOrNull { key ->
    when (val value = this[key]) {
        is JsonPrimitive -> value.intOrNull ?: value.content.filter { it.isDigit() }.toIntOrNull()
        else -> null
    }
}

fun JsonObject.double(vararg keys: String): Double? = keys.firstNotNullOfOrNull { key ->
    when (val value = this[key]) {
        is JsonPrimitive -> value.doubleOrNull
            ?: value.content.replace(',', '.').filter { it.isDigit() || it == '.' }.toDoubleOrNull()

        else -> null
    }
}

fun JsonObject.floatValue(vararg keys: String): Float? = double(*keys)?.toFloat()

fun JsonObject.stringList(vararg keys: String): List<String> = keys.firstNotNullOfOrNull { key ->
    (this[key] as? JsonArray)?.mapNotNull { element ->
        when (element) {
            is JsonPrimitive -> element.content.takeIf { it.isNotBlank() }
            is JsonObject -> element.string("name", "title", "text", "value")
            else -> null
        }
    }
}.orEmpty()

fun JsonObject.elementList(vararg keys: String): List<JsonElement> = keys.firstNotNullOfOrNull { key ->
    (this[key] as? JsonArray)?.toList()
}.orEmpty()

fun JsonElement.primitiveContentOrNull(): String? =
    (this as? JsonPrimitive)?.jsonPrimitive?.content?.takeIf { it.isNotBlank() }
