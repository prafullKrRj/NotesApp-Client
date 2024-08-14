package com.prafull.notesapp.managers

import org.commonmark.parser.Parser
import org.commonmark.renderer.text.TextContentRenderer

fun markdownToPlainText(markdown: String): String {
    val parser = Parser.builder().build()
    val document = parser.parse(markdown)
    val renderer = TextContentRenderer.builder().build()
    return renderer.render(document)
}