import kotlin.math.*
import kotlin.random.*

const val variant = 3

const val yMin = (20 - variant) * 10
const val yMax = (30 - variant) * 10

const val m = 6
const val p = 0.95f
const val rCritical = 2.1f

const val x1Min =  -20
const val x1Max = 30
const val x2Min = -20
const val x2Max = 40

fun variance(yArray: IntArray, yAverage: Float): Float {
    var result = 0f

    for (i in 0 until m) {
        result += (yArray[i] - yAverage).pow(2)
    }

    return result
}

fun main() {
    val yMatrix = Array(3) { IntArray(m) { Random.nextInt(yMin, yMax) } }
    val yAveragesArray = Array(3) { yMatrix[it].sum() / m.toFloat() }

    val sigmasArray = arrayOf(
        variance(yMatrix[0], yAveragesArray[0]),
        variance(yMatrix[1], yAveragesArray[1]),
        variance(yMatrix[2], yAveragesArray[2])
    )

    val sigmaTheta = sqrt((2 * (2 * m - 2)) / (m * (m - 4)).toFloat())

    val fuvArray = arrayOf(
        arrayOf(sigmasArray[0], sigmasArray[1]).maxOrNull()!! / arrayOf(sigmasArray[0], sigmasArray[1]).minOrNull()!!,
        arrayOf(sigmasArray[0], sigmasArray[2]).maxOrNull()!! / arrayOf(sigmasArray[0], sigmasArray[2]).minOrNull()!!,
        arrayOf(sigmasArray[2], sigmasArray[1]).maxOrNull()!! / arrayOf(sigmasArray[2], sigmasArray[1]).minOrNull()!!,
    )

    val thetauvArray = arrayOf(
        ((m - 2) / m.toFloat()) * fuvArray[0],
        ((m - 2) / m.toFloat()) * fuvArray[1],
        ((m - 2) / m.toFloat()) * fuvArray[2]
    )

    val ruvArray = arrayOf(
        (thetauvArray[0] - 1).absoluteValue / sigmaTheta,
        (thetauvArray[1] - 1).absoluteValue / sigmaTheta,
        (thetauvArray[2] - 1).absoluteValue / sigmaTheta,
    )

    val mx1 = (-1 + 1 - 1) / 3f
    val mx2 = (-1 - 1 + 1) / 3f

    val my = yAveragesArray.sum() / 3f

    val a1 = (1 + 1 + 1) / 3f
    val a2 = (1 - 1 - 1) / 3f
    val a3 = (1 + 1 + 1) / 3f

    val a11 = ((-1 * yAveragesArray[0]) + (1 * yAveragesArray[1]) - (1 * yAveragesArray[2])) / 3f
    val a22 = ((-1 * yAveragesArray[0]) - (1 * yAveragesArray[1]) + (1 * yAveragesArray[2])) / 3f

    val determinant = ((a1 * a3) + (mx1 * a2 * mx2) + (mx2 * mx1 * a2) - (mx2 * mx2 * a1) - (mx1 * mx1 * a3) - (a2 * a2))

    val b0 = ((my * a1 * a3) + (mx1 * a2 * a22) + (mx2 * a11 * a2) - (a22 * a1 * mx2) - (mx1 * a11 * a3) - (my * a2 * a2)) / determinant
    val b1 = ((a3 * a11) + (a22 * mx1 * mx2) + (a2 * my * mx2) - (mx2 * mx2 * a11) - (a22 * a2) - (mx1 * my * a3)) / determinant
    val b2 = ((a1 * a22) + (a2 * mx1 * my) + (mx1 * mx2 * a11) - (mx2 * my * a1) - (mx1 * mx1 * a22) - (a2 * a11)) / determinant

    val deltaX1 = (x1Max - x1Min).absoluteValue / 2f
    val deltaX2 = (x2Max - x2Min).absoluteValue / 2f

    val x10 = (x1Max + x1Min) / 2f
    val x20 = (x2Max + x2Min) / 2f

    val a0s = b0 - (b1 * x10 / deltaX1) - (b2 * x20 / deltaX2)
    val a1s = b1 / deltaX1
    val a2s = b2 / deltaX2

    for (i in yMatrix) {
        for (j in i) print("$j ")

        println()
    }

    print("\nСередні значення: ")

    for (i in yAveragesArray) print("$i ")

    println("\n\nsigma1 = ${sigmasArray[0]}")
    println("sigma2 = ${sigmasArray[1]}")
    println("sigma3 = ${sigmasArray[2]}\n")

    println("sigmatheta = $sigmaTheta\n")

    println("Fuv1 = ${fuvArray[0]}")
    println("Fuv2 = ${fuvArray[1]}")
    println("Fuv3 = ${fuvArray[2]}\n")

    println("thetauv1 = ${thetauvArray[0]}")
    println("thetauv2 = ${thetauvArray[1]}")
    println("thetauv3 = ${thetauvArray[2]}\n")

    println("Ruv1 = ${ruvArray[0]}")
    println("Ruv2 = ${ruvArray[1]}")
    println("Ruv3 = ${ruvArray[2]}\n")

    if (ruvArray[0] < rCritical && ruvArray[1] < rCritical && ruvArray[2] < rCritical) {
        println("Дисперсія однорідна\n")
    } else {
        println("Дисперсія неоднорідна\n")
    }

    println("a0 = $a0s")
    println("a1 = $a1s")
    println("a2 = $a2s\n")

    println("Натуралізоване рівняння регресії: ($a0s, $a1s, $a2s)\n")

    println("Перевірка натуралізованого рівняння регресії:")
    println("${yAveragesArray[0]} = ${a0s + x1Min * a1s + x2Min * a2s}")
    println("${yAveragesArray[1]} = ${a0s + x1Max * a1s + x2Min * a2s}")
    println("${yAveragesArray[2]} = ${a0s + x1Min * a1s + x2Max * a2s}\n")

    println("b0 = $b0")
    println("b1 = $b1")
    println("b2 = $b2\n")

    println("Нормоване рівняння регресії: ($b0, $b1, $b2)\n")

    println("Перевірка нормованого рівняння регресії:")
    println("${yAveragesArray[0]} = ${b0 - b1 - b2}")
    println("${yAveragesArray[1]} = ${b0 + b1 - b2}")
    println("${yAveragesArray[2]} = ${b0 - b1 + b2}")
}
