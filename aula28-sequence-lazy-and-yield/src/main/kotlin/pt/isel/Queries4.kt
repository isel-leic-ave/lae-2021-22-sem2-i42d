package pt.isel

fun <T, R> Iterable<T>.convertLazy(transform: (T) -> R): Iterable<R> {
    return ConvertIterable<T, R>(this, transform)
}
class ConvertIterable<T, R>(val ts: Iterable<T>, val transform: (T) -> R) : Iterable<R> {
    override fun iterator(): Iterator<R> {
        return ConvertIterator(ts.iterator(), transform)
    }
}
class ConvertIterator<T, R>(val iterator: Iterator<T>, val transform: (T) -> R) : Iterator<R> {
    override fun hasNext(): Boolean  = iterator.hasNext()

    override fun next(): R = transform(iterator.next())
}

fun <T> Iterable<T>.whereLazy(predicate: (T) -> Boolean) = object : Iterable<T> {
    override fun iterator() = object : Iterator<T> {
        private val iterator = this@whereLazy.iterator()
        private var curr = advance()

        private fun advance(): T? {
            while(iterator.hasNext()) {
                val item = iterator.next()
                if(predicate(item))
                    return item
            }
            return null
        }
        override fun hasNext(): Boolean = curr != null

        override fun next(): T {
            val item: T = curr ?: throw Exception("Out of bounds!")
            curr = advance()
            return item
        }
    }
}
