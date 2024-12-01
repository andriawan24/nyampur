package id.nisyafawwaz.nyampur.ui

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AccountManager : KoinComponent {

    private val client: SupabaseClient by inject()

    fun getCurrentUser(): UserInfo? {
        return client.auth.currentSessionOrNull()?.user
    }
}
