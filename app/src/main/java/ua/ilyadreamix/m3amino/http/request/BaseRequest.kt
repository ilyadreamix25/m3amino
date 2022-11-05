package ua.ilyadreamix.m3amino.http.request

import com.google.gson.Gson
import retrofit2.Response
import ua.ilyadreamix.m3amino.http.model.BasicResponseModel

enum class ResponseState { OK, BAD }

class BaseResponse<T>(
    val state: ResponseState,
    val error: BasicResponseModel?,
    val data: T?
)

open class BaseRequest(
    open val deviceId: String,
    open val userAgent: String,
    open val acceptLanguage: String,
    open val ndcLang: String
) {
    protected fun <T> getFromResponse(response: Response<T>): BaseResponse<T> {
        return if (!response.isSuccessful) {
            val body = response.errorBody()!!.charStream()

            BaseResponse(
                ResponseState.BAD,
                Gson().fromJson(body, BasicResponseModel::class.java),
                null
            )
        } else {
            val body = response.body()!!

            if ((body as BasicResponseModel).statusCode != 0) {
                // Error
                BaseResponse(
                    ResponseState.BAD,
                    null,
                    response.body()!!
                )
            } else {
                BaseResponse(
                    ResponseState.OK,
                    null,
                    response.body()!!
                )
            }
        }
    }
}