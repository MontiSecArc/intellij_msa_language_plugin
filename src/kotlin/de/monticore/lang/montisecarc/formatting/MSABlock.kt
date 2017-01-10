package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.*
import com.intellij.formatting.templateLanguages.BlockWithParent
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.FormatterUtil
import com.intellij.psi.formatter.common.AbstractBlock
import org.jetbrains.annotations.NotNull
import java.util.*

/**
 *  Copyright 2016 thomasbuning

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

class MSABlock(node: ASTNode, wrap: Wrap?, alignment: Alignment?, @NotNull val settings: CodeStyleSettings) : AbstractBlock(node, wrap, alignment), BlockWithParent {

    override fun setParent(p0: BlockWithParent?) {
        myParent = p0
    }

    override fun getParent(): BlockWithParent {
        return myParent!!
    }

    private var myParent: BlockWithParent? = null

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {

        return MSASpacingProcessor(child1, child2, myNode, settings).getSpacing()
    }

    override fun buildChildren(): List<Block> {
        if (isLeaf) {
            return AbstractBlock.EMPTY
        }
        val tlChildren = ArrayList<Block>()
        var childNode: ASTNode? = node.firstChildNode
        while (childNode != null) {
            if (FormatterUtil.containsWhiteSpacesOnly(childNode)) {
                childNode = childNode.treeNext
                continue
            }
            val childBlock = MSABlock(childNode, createChildWrap(childNode),
                    createChildAlignment(), settings)
            childBlock.parent = this
            tlChildren.add(childBlock)
            childNode = childNode.treeNext
        }
        return tlChildren
    }

    fun createChildWrap(child: ASTNode): Wrap {
        return MSAWrappingProcessor.createChildWrap(child, Wrap.createWrap(WrapType.NONE, false), myNode, settings)
    }

    fun createChildAlignment(): Alignment? {
        return MSAAlignmentProcessor.createChildAlignment(myNode, settings)
    }

    override fun getIndent(): Indent {
        return MSAIndentProcessor.getChildIndent(node)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }
}