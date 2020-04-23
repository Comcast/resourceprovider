package com.xfinity.rpplugin

import java.io.File
import java.util.StringTokenizer

class RClassInfoFileConverter {
    fun convertRClassInfo() {
        parse()
    }

    private fun parse() {
        val rClassInfo = File("/Users/mdappo200/Projects/resourceprovider/sample/build/intermediates/compile_and_runtime_not_namespaced_r_class_jar/debug/rclass.txt").readText()
        val tokenizer = StringTokenizer(rClassInfo, "$")

        val rClassStringVars = mutableListOf<String>()
        val rClassPluralVars = mutableListOf<String>()
        val rClassDrawableVars = mutableListOf<String>()
        val rClassDimenVars = mutableListOf<String>()
        val rClassIntegerVars = mutableListOf<String>()
        val rClassColorVars = mutableListOf<String>()
        val rClassIdVars = mutableListOf<String>()

        while (tokenizer.hasMoreTokens()) {
            val token = tokenizer.nextToken();
            if (token.startsWith(STRING_PREFIX)) {
                parseClass(token, rClassStringVars)
            } else if (token.startsWith(PLURAL_PREFIX)) {
                parseClass(token, rClassPluralVars)
            } else if (token.startsWith(DRAWABLE_PREFIX)) {
                parseClass(token, rClassDrawableVars)
            } else if (token.startsWith(DIMEN_PREFIX)) {
                parseClass(token, rClassDimenVars)
            } else if (token.startsWith(INT_PREFIX)) {
                parseClass(token, rClassIntegerVars)
            } else if (token.startsWith(COLOR_PREFIX)) {
                parseClass(token, rClassColorVars)
            } else if (token.startsWith(ID_PREFIX)) {
                parseClass(token, rClassIdVars)
            }
        }

        rClassStringVars.forEachIndexed {index, name ->
            System.out.println("String $index = $name")
        }

        rClassPluralVars.forEachIndexed {index, name ->
            System.out.println("Plural $index = $name")
        }

        rClassDrawableVars.forEachIndexed {index, name ->
            System.out.println("Drawable $index = $name")
        }

        rClassDimenVars.forEachIndexed {index, name ->
            System.out.println("Dimen $index = $name")
        }

        rClassIntegerVars.forEachIndexed {index, name ->
            System.out.println("Int $index = $name")
        }

        rClassColorVars.forEachIndexed {index, name ->
            System.out.println("Color $index = $name")
        }

        rClassIdVars.forEachIndexed {index, name ->
            System.out.println("Id $index = $name")
        }
    }

    private fun parseClass(classString: String, varsList: MutableList<String>){
        val varTokenizer = StringTokenizer(classString, ";")
        val rawVarsList = mutableListOf<String>()
        while (varTokenizer.hasMoreTokens()) {
            val varToken = varTokenizer.nextToken()
            val varName = varToken.substringAfter(VAR_PREFIX, "missing").trim(';')
            if (varName != "missing") {
                rawVarsList.add(varName)
            }
        }
        varsList.addAll(rawVarsList.distinct())
    }

    companion object {
        const val STRING_PREFIX = "string {"
        const val PLURAL_PREFIX = "plurals {"
        const val DRAWABLE_PREFIX = "drawable {"
        const val DIMEN_PREFIX = "dimen {"
        const val INT_PREFIX = "integer {"
        const val COLOR_PREFIX = "color {"
        const val ID_PREFIX = "id {"
        const val VAR_PREFIX = "public static final int "
    }
}

data class RClassInfo(private val rClassStringVars: List<String>,
                      private val rClassPluralVars: List<String>,
                      private val rClassDrawableVars: List<String>,
                      private val rClassDimenVars: List<String>,
                      private val rClassIntegerVars: List<String>,
                      private val rClassColorVars: List<String>,
                      private val rClassIdVars: List<String>)