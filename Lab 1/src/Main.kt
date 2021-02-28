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

    val x01 = x0Find(X1)
    val x02 = x0Find(X2)
    val x03 = x0Find(X3)

    val dx1 = dxFind(x01, X1)
    val dx2 = dxFind(x02, X2)
    val dx3 = dxFind(x03, X3)

    val Xn1 = xNormArrayFind(x01, dx1, X1)
    val Xn2 = xNormArrayFind(x02, dx2, X2)
    val Xn3 = xNormArrayFind(x03, dx3, X3)

    val Yet = YFind(a0, a1, a2, a3, x01, x02, x03)

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
                "${String.format("%.4f", Xn1[i]).padEnd(9)} |  " +
                "${String.format("%.4f", Xn2[i]).padEnd(9)} |  " +
                String.format("%.4f", Xn3[i])
        )
    }

    println("${"x0".padEnd(2)} |  " +
            "${x01.toString().padEnd(5)} |  " +
            "${x02.toString().padEnd(5)} |  " +
            "${x03.toString().padEnd(5)} |  ")

    println("${"dx".padEnd(2)} |  " +
            "${dx1.toString().padEnd(5)} |  " +
            "${dx2.toString().padEnd(5)} |  " +
            "${dx3.toString().padEnd(5)} |")

    println("\nYet = $Yet")
    println("Selection criterion: max(Y). Optimal point: ($optimalPointOfX1, $optimalPointOfX2, $optimalPointOfX3)")
}