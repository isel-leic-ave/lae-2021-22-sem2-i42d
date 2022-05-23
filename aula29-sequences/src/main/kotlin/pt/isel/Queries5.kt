package pt.isel

import pt.isel.sample.Student

fun <T, R> Sequence<T>.convert(transform: (T) -> R): Sequence<R> {
    return sequence {
        for (item in this@convert) {
            yield(transform(item))
        }
    }
}

fun <T> Sequence<T>.where(predicate: (T) -> Boolean): Sequence<T> {
    return sequence {
        for (item in this@where) {
            if(predicate(item))
                yield(item)
        }
    }
}

fun <T> Iterable<T>.concatLazy(other: Iterable<T>) = object : Iterable<T> {
    override fun iterator() = object : Iterator<T> {
        private val iteratorThis = this@concatLazy.iterator()
        private val iteratorOther = other.iterator()

        override fun hasNext(): Boolean = iteratorThis.hasNext() || iteratorOther.hasNext()

        override fun next(): T {
            if(iteratorThis.hasNext()) return iteratorThis.next()
            if(iteratorOther.hasNext()) return iteratorOther.next()
            throw Exception("Out of bounds!")
        }
    }
}

fun <T> Sequence<T>.concat(other: Sequence<T>) : Sequence<T> {
    return sequence {
        for (item in this@concat){
            yield(item)
        }
        for(item in other){
            yield(item)
        }
    }
}
