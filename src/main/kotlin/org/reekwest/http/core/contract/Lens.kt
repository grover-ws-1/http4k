package org.reekwest.http.core.contract

import org.reekwest.http.core.Request
import org.reekwest.http.core.Response

abstract class Lens<in IN, in OUT, FINAL>(val meta: Meta, private val spec: LensSpec<IN, OUT>) {
    operator fun invoke(target: IN): FINAL = try {
        convert(spec.fn(target, meta.name))
    } catch (e: Missing) {
        throw e
    } catch (e: Exception) {
        throw Invalid(meta)
    }

    abstract internal fun convert(o: List<OUT?>?): FINAL

    operator fun <R : IN> invoke(target: R, value: FINAL): R = TODO()
}

operator fun <T, FINAL> Request.get(lens: Lens<Request, T, FINAL>): FINAL = lens(this)
operator fun <T, FINAL> Response.get(lens: Lens<Response, T, FINAL>): FINAL = lens(this)
