package file

import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.CredentialItem
import org.eclipse.jgit.transport.URIish

/**
 * Uses the user's configured Git credential helpers for JGit HTTPS requests.
 * This lets helpers such as Git Credential Manager supply existing tokens or
 * start their normal interactive sign-in flow without storing credentials in Grill.
 */
internal object GitCredentialProvider : CredentialsProvider() {
    override fun isInteractive(): Boolean = true

    override fun supports(vararg items: CredentialItem): Boolean =
        items.all { it is CredentialItem.Username || it is CredentialItem.Password }

    override fun get(uri: URIish, vararg items: CredentialItem): Boolean {
        if (!uri.scheme.equals("https", ignoreCase = true) || uri.host.isNullOrBlank()) return false

        return try {
            val input = buildString {
                append("protocol=https\n")
                val host = uri.host + if (uri.port > 0) ":${uri.port}" else ""
                append("host=$host\n")
                uri.path?.removePrefix("/")?.takeIf(String::isNotBlank)?.let { append("path=$it\n") }
                uri.user?.takeIf(String::isNotBlank)?.let { append("username=$it\n") }
                append('\n')
            }
            val process = ProcessBuilder("git", "credential", "fill")
                .redirectErrorStream(true)
                .start()
            process.outputStream.bufferedWriter(Charsets.UTF_8).use { it.write(input) }
            val output = process.inputStream.bufferedReader(Charsets.UTF_8).use { it.readLines() }
            if (process.waitFor() != 0) return false

            val values = output.mapNotNull { line ->
                val separator = line.indexOf('=')
                if (separator <= 0) null else line.substring(0, separator) to line.substring(separator + 1)
            }.toMap()
            val username = values["username"] ?: return false
            val password = values["password"] ?: return false
            items.forEach { item ->
                when (item) {
                    is CredentialItem.Username -> item.value = username
                    is CredentialItem.Password -> item.value = password.toCharArray()
                }
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    override fun reset(uri: URIish?) = Unit
}
