package com.yk.silence.customnode.net.Exception

import java.lang.RuntimeException

/**
 * 异常
 */
class ApiException(var code: Int, override var message: String) : RuntimeException()