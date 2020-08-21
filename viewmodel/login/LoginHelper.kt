package com.yk.silence.customnode.viewmodel.login


class LoginHelper private constructor() {


    companion object {
        val instance: LoginHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LoginHelper()
        }


    }


}