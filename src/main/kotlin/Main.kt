import java.math.BigInteger
import kotlin.system.measureNanoTime

fun main() {
    val elvis = testElvis()
    val default = testDefault()

    if (elvis < default) {
        println("Elvis is faster than default by ${default - elvis}ns")
    } else {
        println("Default is faster than elvis by ${elvis - default}ns")
    }
}

private fun testElvis(): BigInteger {
    val x: Int? = null
    val totalTimes = 1_000_000
    val times = Int.MAX_VALUE
    var elvisTotal = BigInteger.ZERO

    repeat(totalTimes) {
        val elvisTime = measureNanoTime {
            repeat(times) {
                x ?: 0
            }
        }

        elvisTotal += elvisTime

        progress(it + 1, totalTimes)
    }

    println()
    println("Elvis total:\t\t${elvisTotal}ns")

    return elvisTotal
}

private fun testDefault(): BigInteger {
    val x: Int? = null
    val totalTimes = 1_000_000
    val times = Int.MAX_VALUE
    var defaultTotal = BigInteger.ZERO

    repeat(totalTimes) {
        val defaultTime = measureNanoTime {
            repeat(times) {
                x.orDefault(0)
            }
        }

        defaultTotal += defaultTime

        progress(it + 1, totalTimes)
    }

    println()
    println("Default total:\t\t${defaultTotal}ns")

    return defaultTotal
}

private fun Int?.orDefault(default: Int) = this ?: default

private fun progress(progress: Int, total: Int) {
    val percent = (progress.toDouble() / total.toDouble() * 100).toInt()
    val bar = (0..100).map { if (it < percent) '=' else if (percent == it) '>' else ' ' }.joinToString("")
    print("\r[$bar] $percent% ($progress/$total)")
}

private operator fun BigInteger.plus(other: Long) = this.add(other.toBigInteger())

