package de.monticore.lang.montisecarc.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import de.monticore.lang.montisecarc.MSAIcons
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.Icon

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
class MSAColorSettingsPage: ColorSettingsPage {

    private val DESCRIPTORS = arrayOf(AttributesDescriptor("Key", MSASyntaxHighlighter.KEY),
            AttributesDescriptor("String", MSASyntaxHighlighter.STRING),
            AttributesDescriptor("Comment", MSASyntaxHighlighter.SINGLE_LINE_COMMENT),
            AttributesDescriptor("Level", MSASyntaxHighlighter.LEVEL),
            AttributesDescriptor("Component Name", MSASyntaxHighlighter.COMPONENT_NAME),
            AttributesDescriptor("Parameters", MSASyntaxHighlighter.TYPES),
            AttributesDescriptor("Componenent Instance Name", MSASyntaxHighlighter.COMPONENT_INSTANCE_NAME)
            )

    @Nullable
    override fun getIcon(): Icon {
        return MSAIcons.FILE
    }

    @NotNull
    override fun getHighlighter(): SyntaxHighlighter {
        return MSASyntaxHighlighter()
    }

    @NotNull
    override fun getDemoText(): String {
        return "package secarc.supermarket;\n" +
                "\n" +
                "import secarc.supermarket.msg.*;\n" +
                "\n" +
                "import secarc.supermarket.imp.CashDesk;\n" +
                "import secarc.supermarket.imp.WebSite;\n" +
                "\n" +
                "component Supermarket {\n" +
                "\n" +
                "	trustlevel +0;\n" +
                "	accesscontrol off;\n" +
                "	\n" +
                "	// cash desk ports	\n" +
                "	port\n" +
                "		in Event newSale,\n" +
                "		in Image barcode,\n" +
                "		out ProductData outPDataNS,\n" +
                "		in Event endSale;\n" +
                "		\n" +
                "	port\n" +
                "		in Event payCash,\n" +
                "		in Double amountMoney,\n" +
                "		out Double outAmountChange;\n" +
                "		\n" +
                "	port\n" +
                "		in Event payCard,\n" +
                "		in CardData cardDataPC,\n" +
                "		in Integer pinPC,\n" +
                "		out Boolean outValidationPC;\n" +
                "	port \n" +
                "		in Event payOnline,\n" +
                "		in AccountData accDataPO,\n" +
                "		in Integer pinPO,\n" +
                "		out Boolean outValidationPO;\n" +
                "	\n" +
                "	port\n" +
                "		in Event getCash,\n" +
                "		in CardData cDataGC,\n" +
                "		in Integer pinGC,\n" +
                "		out Double outAmountMoney;\n" +
                "		\n" +
                "	component CashDesk cashDesk;\n" +
                "	\n" +
                "	connect newSale -> cashDesk.newSale;\n" +
                "	connect barcode -> cashDesk.barcode;\n" +
                "	connect cashDesk.outPDataNS -> outPDataNS;\n" +
                "	connect endSale -> cashDesk.endSale;\n" +
                "	\n" +
                "	connect payCash -> cashDesk.payCash;\n" +
                "	connect amountMoney -> cashDesk.amountMoney;\n" +
                "	connect cashDesk.outAmountChange -> outAmountChange;\n" +
                "	\n" +
                "	connect payCard -> cashDesk.payCard;\n" +
                "	connect cardDataPC -> cashDesk.cardDataPC;\n" +
                "	connect pinPC -> cashDesk.pinPC;\n" +
                "	connect cashDesk.outValidationPC -> outValidationPC;\n" +
                "\n" +
                "	connect payOnline -> cashDesk.payOnline;\n" +
                "	connect accDataPO -> cashDesk.accDataPO;\n" +
                "	connect pinPO -> cashDesk.pinPO;\n" +
                "	connect cashDesk.outValidationPO -> outValidationPO;	\n" +
                "	\n" +
                "	connect getCash -> cashDesk.getCash;\n" +
                "	connect cDataGC -> cashDesk.cDataGC;\n" +
                "	connect pinGC -> cashDesk.pinGC;\n" +
                "	connect cashDesk.outAmountMoney -> outAmountMoney;\n" +
                "	\n" +
                "	// web service ports\n" +
                "	port\n" +
                "		in Event newOrder,\n" +
                "		in Integer productId,\n" +
                "		out ProductData outPDataNO,\n" +
                "		in Event endOrder;\n" +
                "	port \n" +
                "		in Event payOnlineWeb,\n" +
                "		in AccountData accDataPOW,\n" +
                "		in Integer pinPOW,\n" +
                "		out Boolean outValidationPOW;\n" +
                "\n" +
                "	component WebSite webSite;\n" +
                "	\n" +
                "	connect newOrder -> webSite.newOrder;\n" +
                "	connect productId -> webSite.productId;\n" +
                "	connect webSite.outPDataNO -> outPDataNO;\n" +
                "	connect endOrder -> webSite.endOrder;\n" +
                "	\n" +
                "	connect payOnlineWeb -> webSite.payOnlineWeb;\n" +
                "	connect accDataPOW -> webSite.accDataPOW;\n" +
                "	connect pinPOW -> webSite.pinPOW;\n" +
                "	connect webSite.outValidationPOW -> outValidationPOW;\n" +
                "	\n" +
                "\n" +
                "}"
    }

    @Nullable
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    @NotNull
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    @NotNull
    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    @NotNull
    override fun getDisplayName(): String {
        return "MontiSecArc"
    }
}