package id.jasoet.extractor.core.util


/**
 * Executes the given block and returns elapsed time in milliseconds and Block Result.
 */
public inline fun <R> measureExecutionMillis(block: () -> R): Pair<Long, R> {
    val start = System.currentTimeMillis()
    val result = block()
    return (System.currentTimeMillis() - start) to result
}

/**
 * Executes the given block and returns elapsed time in nanoseconds and Block Result.
 */
public inline fun <R> measureExecutionNano(block: () -> R): Pair<Long, R> {
    val start = System.nanoTime()
    val result = block()
    return (System.nanoTime() - start) to result
}
 

