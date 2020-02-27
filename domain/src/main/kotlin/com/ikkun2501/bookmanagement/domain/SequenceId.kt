package com.ikkun2501.bookmanagement.domain

data class SequenceId<T>(val value: Int) : Comparable<SequenceId<T>> {
    override fun compareTo(other: SequenceId<T>): Int {
        return this.value.compareTo(other.value)
    }

    override fun toString(): String {
        return value.toString()
    }

    fun notAssing(): Boolean {
        return NOT_ASSIGNED == this
    }

    companion object {
        private val NOT_ASSIGNED: SequenceId<Any> =
            SequenceId(-1)

        fun <R> notAssigned(): SequenceId<R> {
            @Suppress("UNCHECKED_CAST")
            return NOT_ASSIGNED as SequenceId<R>
        }
    }
}
