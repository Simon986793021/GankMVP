package com.smartcentury.kcwork.http.exception


/**
 * 网络请求异常处理
 * @author vincentkin038
 */
class ApiException : RuntimeException {

    /**
     * 构造函数
     * @param code 错误码
     */
    constructor(code: Int) : super(getErrorDesc(code))

    constructor(msg: String) : super(msg)

    companion object {


        private val serialVersionUID = -6712767999283972788L
        /**
         * 获取错误码对应的文字提示
         * @param code 错误码
         * @return   文字提示
         */
        private fun getErrorDesc(code: Int): String {
            return ""
        }
    }
}