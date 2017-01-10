
package de.monticore.lang.montisecarc.cpe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.monticore.lang.montisecarc.cpe package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CpeList_QNAME = new QName("http://cpe.mitre.org/dictionary/2.0", "cpe-list");
    private final static QName _CpeItem_QNAME = new QName("http://cpe.mitre.org/dictionary/2.0", "cpe-item");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.monticore.lang.montisecarc.cpe
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReferencesType }
     * 
     */
    public ReferencesType createReferencesType() {
        return new ReferencesType();
    }

    /**
     * Create an instance of {@link ListType }
     * 
     */
    public ListType createListType() {
        return new ListType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link CheckType }
     * 
     */
    public CheckType createCheckType() {
        return new CheckType();
    }

    /**
     * Create an instance of {@link NotesType }
     * 
     */
    public NotesType createNotesType() {
        return new NotesType();
    }

    /**
     * Create an instance of {@link GeneratorType }
     * 
     */
    public GeneratorType createGeneratorType() {
        return new GeneratorType();
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link ReferencesType.Reference }
     * 
     */
    public ReferencesType.Reference createReferencesTypeReference() {
        return new ReferencesType.Reference();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cpe.mitre.org/dictionary/2.0", name = "cpe-list")
    public JAXBElement<ListType> createCpeList(ListType value) {
        return new JAXBElement<ListType>(_CpeList_QNAME, ListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cpe.mitre.org/dictionary/2.0", name = "cpe-item")
    public JAXBElement<ItemType> createCpeItem(ItemType value) {
        return new JAXBElement<ItemType>(_CpeItem_QNAME, ItemType.class, null, value);
    }

}
