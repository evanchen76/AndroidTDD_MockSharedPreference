package evan.chen.tutorial.tdd.mocksharedpreferencesample

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class RepositoryTest {

    @Test
    fun saveUserId() {
        // 1. Mock Context and SharePreference
        val sharedPrefs = mockk<SharedPreferences>(relaxed = true)
        val sharedPrefsEditor = mockk<SharedPreferences.Editor>(relaxed = true)
        val context = mockk<Context>(relaxed = true)

        // 2. Use every returns to return mocking sharedPreference.
        every{context.getSharedPreferences(any(), any())}.returns(sharedPrefs)
        every{sharedPrefs.edit()}.returns(sharedPrefsEditor)
        every{sharedPrefsEditor.putString(any(), any())}.returns(sharedPrefsEditor)

        val userId = "A1234567"
        val preKey = "USER_ID"

        // 3. Call tested object repository.saveUserId(userId)
        val repository = Repository(context)
        repository.saveUserId(userId)

        // 4. Use verify to check  sharedPrefsEditor.putString() is pass right parameter.
        verify{sharedPrefsEditor.putString(eq(preKey), eq(userId)) }

        // 5. Use verify to check sharedPrefsEditor.commit() is called.
        verify{sharedPrefsEditor.commit()}
    }
}