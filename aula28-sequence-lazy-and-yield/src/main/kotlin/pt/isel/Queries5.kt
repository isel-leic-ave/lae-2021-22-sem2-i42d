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
