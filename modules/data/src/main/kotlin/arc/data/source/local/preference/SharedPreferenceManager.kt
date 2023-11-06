package arc.data.source.local.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.DEFAULT_MASTER_KEY_ALIAS
import com.google.gson.Gson
import kotlin.Float.Companion.MIN_VALUE

/**
 * [SharedPreferenceManager] is [android.content.SharedPreferences] class within Arc
 * @author jimlyas
 * @since 0.1.0
 * @param context [Context] used to create the instance of [android.content.SharedPreferences]
 * @param preferenceName [String] name for the [android.content.SharedPreferences]
 * @param encrypted [Boolean] to define does the [android.content.SharedPreferences] is encrypted or not,
 * if true, will use [EncryptedSharedPreferences]. And if not, will use default [android.content.SharedPreferences] from [Context]
 * @param gson [Gson] instance that used for parsing object to [String]
 * @property preference instance of [android.content.SharedPreferences], will differ based on encryption
 * @property editor instance of [android.content.SharedPreferences.Editor] that will be used to edit
 * value, use lazy initialization so will not created unless called
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class SharedPreferenceManager(
	context: Context,
	preferenceName: String,
	encrypted: Boolean = false,
	val gson: Gson = Gson()
) {

	private val preference = if (encrypted) {
		EncryptedSharedPreferences.create(
			context,
			preferenceName,
			MasterKey.Builder(context, DEFAULT_MASTER_KEY_ALIAS).build(),
			AES256_SIV,
			AES256_GCM
		)
	} else context.getSharedPreferences(preferenceName, MODE_PRIVATE)

	private val editor by lazy { preference.edit() }

	/**
	 * Function to set a [String] value to [preference]
	 * @param key [String] key for calling it later
	 * @param value [String] value to store
	 */
	fun setString(key: String, value: String) {
		editor.putString(key, value).apply()
	}

	/**
	 * Function to get [String] value from [preference]
	 * @param key [String] key to refer
	 * @param defaultValue [String] to return if the [key] doesn't return any value
	 * @return [String] from given [key] if exist, [defaultValue] if not
	 */
	fun getString(key: String, defaultValue: String = "") = preference.getString(key, defaultValue)

	/**
	 * Function to set a [Int] value to [preference]
	 * @param key [String] key for calling it later
	 * @param value [Int] value to store
	 */
	fun setInt(key: String, value: Int) {
		editor.putInt(key, value).apply()
	}

	/**
	 * Function to get [Int] value from [preference]
	 * @param key [String] key to refer
	 * @param defaultValue [Int] to return if the [key] doesn't return any value
	 * @return [Int] from given [key] if exist, [defaultValue] if not
	 */
	fun getInt(key: String, defaultValue: Int = 0) = preference.getInt(key, defaultValue)

	/**
	 * Function to set a [Long] value to [preference]
	 * @param key [String] key for calling it later
	 * @param value [Long] value to store
	 */
	fun setLong(key: String, value: Long) {
		editor.putLong(key, value).apply()
	}

	/**
	 * Function to get [Long] value from [preference]
	 * @param key [String] key to refer
	 * @param defaultValue [Long] to return if the [key] doesn't return any value
	 * @return [Long] from given [key] if exist, [defaultValue] if not
	 */
	fun getLong(key: String, defaultValue: Long = 0L) = preference.getLong(key, defaultValue)

	/**
	 * Function to set a [Boolean] value to [preference]
	 * @param key [String] key for calling it later
	 * @param value [Boolean] value to store
	 */
	fun setBoolean(key: String, value: Boolean) {
		editor.putBoolean(key, value).apply()
	}

	/**
	 * Function to get [Boolean] value from [preference]
	 * @param key [String] key to refer
	 * @param defaultValue [Boolean] to return if the [key] doesn't return any value
	 * @return [Boolean] from given [key] if exist, [defaultValue] if not
	 */
	fun getBoolean(key: String, defaultValue: Boolean = false) =
		preference.getBoolean(key, defaultValue)

	/**
	 * Function to set a [Float] value to [preference]
	 * @param key [String] key for calling it later
	 * @param value [Float] value to store
	 */
	fun setFloat(key: String, value: Float) {
		editor.putFloat(key, value).apply()
	}

	/**
	 * Function to get [Float] value from [preference]
	 * @param key [String] key to refer
	 * @param defaultValue [Float] to return if the [key] doesn't return any value
	 * @return [Float] from given [key] if exist, [defaultValue] if not
	 */
	fun getFloat(key: String, defaultValue: Float = MIN_VALUE) =
		preference.getFloat(key, defaultValue)

	/**
	 * Function to set a [Object] value to [preference]
	 * @param key [String] key for calling it later
	 * @param value [model] value to store
	 * @param model Class to map into json object
	 */
	fun <model> set(key: String, value: model) {
		editor.putString(key, gson.toJson(value)).apply()
	}

	/**
	 * Function to get [model] value from [preference]
	 * @param key [String] key to refer
	 * @return [model] from given [key] if exist, null if not
	 */
	inline fun <reified model> get(key: String): model? = try {
		gson.fromJson(getString(key, ""), model::class.java)
	} catch (t: Throwable) {
		null
	}

	/**
	 * Function to remove value from [preference] based on given [key]
	 * @param key [String] key of the value to remove
	 */
	fun remove(key: String) {
		editor.remove(key).apply()
	}

	/**
	 * Function to clear all values from the [preference]
	 */
	fun clear() {
		editor.clear().apply()
	}
}