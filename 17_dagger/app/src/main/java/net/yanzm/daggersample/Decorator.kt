package net.yanzm.daggersample

interface Decorator {
    fun decorate(text: String): String
}

class SharpSurroundDecorator : Decorator {

    override fun decorate(text: String): String {
        val length = text.length
        val sb = StringBuilder()
        repeat(length + 4) { sb.append("#") }
        sb.append("\n")
        sb.append("# $text #\n")
        repeat(length + 4) { sb.append("#") }
        return sb.toString()
    }
}
