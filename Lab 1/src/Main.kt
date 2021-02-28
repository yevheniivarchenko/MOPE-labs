import kotlin.random.Random

fun x0Find(X: IntArray): Float {
    return (X.maxOrNull()!! - X.minOrNull()!!) / 2.0f
}

fun dxFind(x0: Float, X: IntArray): Float {
    return X.maxOrNull()!! - x0
}

fun xNormArrayFind(x0: Float, dx: Float, X: IntArray): FloatArray {
    return FloatArray(8) { (X[it] - x0) / dx }
}

fun YFind(a0: Int, a1: Int, a2: Int, a3: Int, X1: Float, X2: Float, X3: Float): Float {
    return a0 + a1 * X1 + a2 * X2 + a3 * X3
}

fun maxYFind(Y: FloatArray): Float {
    return Y.maxOrNull()!!
}

fun main() {
    val min = 0
    val max = 50

    val a0: Int = Random.nextInt(min, max)
    val a1: Int = Random.nextInt(min, max)
    val a2: Int = Random.nextInt(min, max)
    val a3: Int = Random.nextInt(min, max)

    val X1 = IntArray(8) { Random.nextInt(min, max) }
    val X2 = IntArray(8) { Random.nextInt(min, max) }
    val X3 = IntArray(8) { Random.nextInt(min, max) }

    val Y = FloatArray(8) { YFind(a0, a1, a2, a3, X1[it].toFloat(), X2[it].toFloat(), X3[it].toFloat()) }

    val x0 = floatArrayOf(x0Find(X1), x0Find(X2), x0Find(X3))
    val dx = floatArrayOf(dxFind(x0[0], X1), dxFind(x0[1], X2), dxFind(x0[2], X3))
    val Xn = arrayOf(xNormArrayFind(x0[0], dx[0], X1), xNormArrayFind(x0[1], dx[1], X2), xNormArrayFind(x0[2], dx[2], X3))

    val Yet = YFind(a0, a1, a2, a3, x0[0], x0[1], x0[2])

    val optimalPointOfX1 = X1[Y.indexOfFirst { it == maxYFind(Y) }]
    val optimalPointOfX2 = X2[Y.indexOfFirst { it == maxYFind(Y) }]
    val optimalPointOfX3 = X3[Y.indexOfFirst { it == maxYFind(Y) }]

    println("${"№".padStart(2)} |  " +
            "${"X1".padEnd(5)} |  " +
            "${"X2".padEnd(5)} |  " +
            "${"X3".padEnd(5)} |  " +
            "${"Y".padEnd(7)} |  " +
            "${"Xn1".padEnd(9)} |  " +
            "${"Xn2".padEnd(9)} |  " +
            "Xn3")

    println("-".repeat(78))

    for (i in 0 until 8) {
        println("${(i + 1).toString().padStart(2)} |  " +
                "${X1[i].toString().padEnd(5)} |  " +
                "${X2[i].toString().padEnd(5)} |  " +
                "${X3[i].toString().padEnd(5)} |  " +
                "${Y[i].toString().padEnd(7)} |  " +
                "${String.format("%.4f", Xn[0][i]).padEnd(9)} |  " +
                "${String.format("%.4f", Xn[1][i]).padEnd(9)} |  " +
                String.format("%.4f", Xn[2][i])
        )
    }

    println("${"x0".padEnd(2)} |  " +
            "${x0[0].toString().padEnd(5)} |  " +
            "${x0[1].toString().padEnd(5)} |  " +
            "${x0[2].toString().padEnd(5)} |  ")

    println("${"dx".padEnd(2)} |  " +
            "${dx[0].toString().padEnd(5)} |  " +
            "${dx[1].toString().padEnd(5)} |  " +
            "${dx[2].toString().padEnd(5)} |")

    println("\nYet = $Yet")
    println("Selection criterion: max(Y). Optimal point: ($optimalPointOfX1, $optimalPointOfX2, $optimalPointOfX3)")
}