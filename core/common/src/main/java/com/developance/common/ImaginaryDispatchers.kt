package com.developance.common

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val imaginaryDispatchers: ImaginaryDispatchers)
enum class ImaginaryDispatchers {
    IO
}