package kmp.fbk.kmpartgallery.networking

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo

/**
 * Used to ignore unknown keys on deserialization.
 * Read more here: https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json-ignore-unknown-keys/
 */
@SerialInfo
@Target(allowedTargets = [AnnotationTarget.CLASS])
@ExperimentalSerializationApi
annotation class JsonIgnoreUnknownKeys