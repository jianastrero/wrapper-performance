import kotlin.system.measureNanoTime

fun main() {
    val elvis = testElvis()
    val default = testDefault()

    println("Elvis:\t\t\t$elvis")
    println("Default:\t\t$default")

    if (elvis < default) {
        println("Elvis is faster than default by ${default - elvis}ns")
    } else {
        println("Default is faster than elvis by ${elvis - default}ns")
    }
}

private fun testElvis(): Long {
    val x: Int? = null
    val times = Int.MAX_VALUE

    return measureNanoTime {
        repeat(times) {
            x ?: 0
        }
    }
}

private fun testDefault(): Long {
    val x: Int? = null
    val times = Int.MAX_VALUE

    return measureNanoTime {
        repeat(times) {
            x.orDefault(0)
        }
    }
}

private fun Int?.orDefault(default: Int) = this ?: default

private fun progress(progress: Int, total: Int) {
    val percent = (progress.toDouble() / total.toDouble() * 100).toInt()
    val bar = (0..100).map { if (it < percent) '=' else if (percent == it) '>' else ' ' }.joinToString("")
    print("\r[$bar] $percent% ($progress/$total)")
}

