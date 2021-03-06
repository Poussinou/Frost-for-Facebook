package com.pitchedapps.frost.utils

import android.content.Intent
import android.os.BaseBundle

/**
 * Created by Allan Wang on 29/12/17.
 *
 * Helper to set enum using its name rather than the serialized version
 * Name is used in case the enum is involved in persistent data, where updates may shift indices
 */
interface EnumBundle<E : Enum<E>> {

    val bundleContract: EnumBundleCompanion<E>

    val name: String

    val ordinal: Int

    fun put(intent: Intent) {
        intent.putExtra(bundleContract.argTag, name)
    }

    fun put(bundle: BaseBundle?) {
        bundle?.putString(bundleContract.argTag, name)
    }
}

interface EnumBundleCompanion<E : Enum<E>> {

    val argTag: String

    val values: Array<E>

    val valueMap: Map<String, E>

    operator fun get(name: String?) = if (name == null) null else valueMap[name]

    operator fun get(bundle: BaseBundle?) = get(bundle?.getString(argTag))

    operator fun get(intent: Intent?) = get(intent?.getStringExtra(argTag))

}

open class EnumCompanion<E : Enum<E>>(
        final override val argTag: String,
        final override val values: Array<E>) : EnumBundleCompanion<E> {

    final override val valueMap: Map<String, E> = values.map { it.name to it }.toMap()

    final override fun get(name: String?) = super.get(name)

    final override fun get(bundle: BaseBundle?) = super.get(bundle)

    final override fun get(intent: Intent?) = super.get(intent)

}