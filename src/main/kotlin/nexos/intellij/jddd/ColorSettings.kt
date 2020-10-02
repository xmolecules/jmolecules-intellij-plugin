package nexos.intellij.jddd

import com.intellij.lexer.DummyLexer
import com.intellij.lexer.EmptyLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.psi.tree.IElementType
import javax.swing.Icon

class ColorSettings: ColorSettingsPage {
    private val theAttributeDescriptors by lazy { annotations.map { it.attributesDescriptor }.toTypedArray() }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return theAttributeDescriptors
       // return arrayOf()
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
      // return annotations.map { it.colorDescriptor }.toTypedArray()
        return arrayOf()
    }

    override fun getDisplayName(): String = "jDDD"

    override fun getIcon(): Icon? = null

    override fun getHighlighter() = Highlighter()

    override fun getDemoText(): String = " " //must not be an empty string!

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? {
        return null
    }
}

class Highlighter: SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
       return EmptyLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
      //  return annotations.map { it.key }.toTypedArray()
        return arrayOf()
    }
}
