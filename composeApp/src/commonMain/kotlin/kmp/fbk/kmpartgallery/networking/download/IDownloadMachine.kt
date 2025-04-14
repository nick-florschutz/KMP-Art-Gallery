package kmp.fbk.kmpartgallery.networking.download

import de.jensklingenberg.ktorfit.Call
import de.jensklingenberg.ktorfit.Callback
import io.ktor.client.statement.HttpResponse
import kmp.fbk.kmpartgallery.networking.MetArtApi
import kmp.fbk.kmpartgallery.networking.NetworkRequestBuilder
import kmp.fbk.kmpartgallery.networking.createMetArtApi
import kotlinx.coroutines.CoroutineScope

abstract class DownloadMachine {
    val metArtApi: MetArtApi = NetworkRequestBuilder.requestBuilder.createMetArtApi()
    abstract val coroutineScope: CoroutineScope

    fun <ResponseType> downloadItem(
        apiCall: Call<ResponseType>,
        onSuccessfulDownload: (ResponseType) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        apiCall.onExecute(
            callBack = object : Callback<ResponseType> {
                override fun onError(exception: Throwable) {
                    exception.printStackTrace()
                    onError(exception)
                }

                override fun onResponse(call: ResponseType, response: HttpResponse) {
                    onSuccessfulDownload(call)
                }
            },
        )
    }

}