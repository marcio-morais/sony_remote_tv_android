package app.sonyremote.tv

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import java.net.Inet4Address

/**
 * Finds the phone's Wi-Fi network.
 *
 * With mobile data on, Android may route sockets through the cellular network, which cannot
 * reach the TV on the LAN. Callers bind their sockets to this [Network] to force the Wi-Fi path.
 */
class WifiNetworkHelper(context: Context) {
    private val connectivity =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /** Null when the phone is not on Wi-Fi. allNetworks is deprecated but is the simplest way to list them. */
    @Suppress("DEPRECATION")
    fun wifiNetwork(): Network? = connectivity.allNetworks.firstOrNull { network ->
        connectivity.getNetworkCapabilities(network)
            ?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    /** The phone's own IPv4 on [network]; discovery uses it to derive the /24 to scan. */
    fun localIpv4(network: Network): Inet4Address? =
        connectivity.getLinkProperties(network)
            ?.linkAddresses
            ?.map { it.address }
            ?.filterIsInstance<Inet4Address>()
            ?.firstOrNull()
}
