package com.wtduyuwnt.overrecipes.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wtduyuwnt.overrecipes.data.model.AuthState
import com.wtduyuwnt.overrecipes.data.model.UserAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.security.MessageDigest
import java.security.SecureRandom

private val Context.accountStore: DataStore<Preferences> by preferencesDataStore(name = "account")

class AccountDataStore(private val context: Context) {

    private val nameKey = stringPreferencesKey("account_name")
    private val emailKey = stringPreferencesKey("account_email")
    private val saltKey = stringPreferencesKey("account_salt")
    private val hashKey = stringPreferencesKey("account_hash")
    private val signedInKey = booleanPreferencesKey("account_signed_in")

    private val preferences: Flow<Preferences> = context.accountStore.data
        .catch { error -> if (error is IOException) emit(emptyPreferences()) else throw error }

    val authState: Flow<AuthState> = preferences.map { stored ->
        val name = stored[nameKey]
        val email = stored[emailKey]
        val signedIn = stored[signedInKey] == true
        if (signedIn && name != null && email != null) {
            AuthState.SignedIn(UserAccount(name = name, email = email))
        } else {
            AuthState.SignedOut
        }
    }

    suspend fun hasAccount(): Boolean = preferences.first()[hashKey] != null

    suspend fun savedEmail(): String? = preferences.first()[emailKey]

    suspend fun register(name: String, email: String, password: String) {
        val salt = newSalt()
        context.accountStore.edit { stored ->
            stored[nameKey] = name
            stored[emailKey] = email
            stored[saltKey] = salt
            stored[hashKey] = hash(password, salt)
            stored[signedInKey] = true
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        val stored = preferences.first()
        val savedEmail = stored[emailKey] ?: return false
        val salt = stored[saltKey] ?: return false
        val savedHash = stored[hashKey] ?: return false
        if (!savedEmail.equals(email, ignoreCase = true)) return false
        if (savedHash != hash(password, salt)) return false
        context.accountStore.edit { it[signedInKey] = true }
        return true
    }

    suspend fun signOut() {
        context.accountStore.edit { it[signedInKey] = false }
    }

    private fun newSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return bytes.toHex()
    }

    private fun hash(password: String, salt: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest((salt + password).toByteArray()).toHex()
    }

    private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
}
