package day7

import java.io.File

fun main(args: Array<String>) {
    val lines = readFile()
    val parsedInput = lines.map { parseInput(it) }
    val parsedNodes = parsedInput
        .map { it.toList() }
        .flatten()
        .sortedBy { it }
        .fold(listOf<String>()) { acc, letter -> if(acc.contains(letter)) acc else listOf(*acc.toTypedArray(), letter) }
        .map { Node(it, parsedInput.filter { (_, afterStep) -> afterStep == it }.map { (letter) -> letter })}

    var completedNodes = listOf<String>()
    while ( completedNodes.size != parsedNodes.size ) {
        val nodeToComplete = parsedNodes.first { node -> completedNodes.containsAll(node.beforeSteps) && !completedNodes.contains(node.letter) }
        completedNodes = listOf(*completedNodes.toTypedArray(), nodeToComplete.letter)
    }

    println(completedNodes.reduce { result, node -> result + node })
}

//fun letterToNumber(letter: String) = letter.toInt() - "A".toInt() + 1

fun readFile(): List<String> = File("/Users/joaosantos/Projects/mine/aoc/src/day7/input").readLines()

val REGEX = """Step ([A-Z]) must be finished before step ([A-Z]) can begin.""".toRegex()
fun parseInput(line: String) = REGEX.find(line)!!.destructured

data class Node(val letter: String, val beforeSteps: List<String>)

//BHMOTUFLCPQKWINZVRXAJDSYEG