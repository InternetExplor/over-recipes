package com.wtduyuwnt.overrecipes.util

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toUserMessage(): String = when (this) {
    is UnknownHostException -> "Нет соединения с интернетом"
    is SocketTimeoutException -> "Сервер не отвечает, попробуйте ещё раз"
    is HttpException -> when (code()) {
        404 -> "Не найдено на сервере"
        422 -> "Сервер не принял запрос — уточните поиск"
        in 500..599 -> "Сервер временно недоступен"
        else -> "Ошибка запроса (${code()})"
    }

    is IOException -> "Проблема с сетью"
    else -> "Что-то пошло не так"
}
