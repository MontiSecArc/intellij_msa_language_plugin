
package de.monticore.lang.montisecarc.cpe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Element;


/**
 * The ItemType complex type defines an element that represents a single CPE
 *                 Name. The required name attribute is a URI which must be a unique key and should follow the URI
 *                 structure outlined in the CPE Specification. The optional title element is used to provide a
 *                 human-readable title for the platform. To support uses intended for multiple languages, this element
 *                 supports the ‘xml:lang’ attribute. At most one title element can appear for each language. The notes
 *                 element holds optional descriptive material. Multiple notes elements are allowed, but only one per
 *                 language should be used. Note that the language associated with the notes element applies to all child
 *                 note elements. The optional references element holds external info references. The optional check
 *                 element is used to call out an OVAL Definition that can confirm or reject an IT system as an instance of
 *                 the named platform. Additional elements not part of the CPE namespace are allowed and are just skipped
 *                 by validation. In essence, a dictionary file can contain additional information that a user can choose
 *                 to use or not, but this information is not required to be used or understood.
 * 
 * <p>Java-Klasse für ItemType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://cpe.mitre.org/dictionary/2.0}TextType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://cpe.mitre.org/dictionary/2.0}NotesType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="references" type="{http://cpe.mitre.org/dictionary/2.0}ReferencesType" minOccurs="0"/>
 *         &lt;element name="check" type="{http://cpe.mitre.org/dictionary/2.0}CheckType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://cpe.mitre.org/naming/2.0}cpe22Type" />
 *       &lt;attribute name="deprecated" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="deprecated_by" type="{http://cpe.mitre.org/naming/2.0}cpe22Type" />
 *       &lt;attribute name="deprecation_date" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemType", namespace = "http://cpe.mitre.org/dictionary/2.0", propOrder = {
    "title",
    "notes",
    "references",
    "check",
    "any"
})
public class ItemType {

    @XmlElement(namespace = "http://cpe.mitre.org/dictionary/2.0")
    protected List<TextType> title;
    @XmlElement(namespace = "http://cpe.mitre.org/dictionary/2.0")
    protected List<NotesType> notes;
    @XmlElement(namespace = "http://cpe.mitre.org/dictionary/2.0")
    protected ReferencesType references;
    @XmlElement(namespace = "http://cpe.mitre.org/dictionary/2.0")
    protected List<CheckType> check;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "deprecated")
    protected Boolean deprecated;
    @XmlAttribute(name = "deprecated_by")
    protected String deprecatedBy;
    @XmlAttribute(name = "deprecation_date")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deprecationDate;

    /**
     * Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getTitle() {
        if (title == null) {
            title = new ArrayList<TextType>();
        }
        return this.title;
    }

    /**
     * Gets the value of the notes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NotesType }
     * 
     * 
     */
    public List<NotesType> getNotes() {
        if (notes == null) {
            notes = new ArrayList<NotesType>();
        }
        return this.notes;
    }

    /**
     * Ruft den Wert der references-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ReferencesType }
     *     
     */
    public ReferencesType getReferences() {
        return references;
    }

    /**
     * Legt den Wert der references-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencesType }
     *     
     */
    public void setReferences(ReferencesType value) {
        this.references = value;
    }

    /**
     * Gets the value of the check property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the check property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCheck().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CheckType }
     * 
     * 
     */
    public List<CheckType> getCheck() {
        if (check == null) {
            check = new ArrayList<CheckType>();
        }
        return this.check;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der deprecated-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDeprecated() {
        if (deprecated == null) {
            return false;
        } else {
            return deprecated;
        }
    }

    /**
     * Legt den Wert der deprecated-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeprecated(Boolean value) {
        this.deprecated = value;
    }

    /**
     * Ruft den Wert der deprecatedBy-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeprecatedBy() {
        return deprecatedBy;
    }

    /**
     * Legt den Wert der deprecatedBy-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeprecatedBy(String value) {
        this.deprecatedBy = value;
    }

    /**
     * Ruft den Wert der deprecationDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeprecationDate() {
        return deprecationDate;
    }

    /**
     * Legt den Wert der deprecationDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeprecationDate(XMLGregorianCalendar value) {
        this.deprecationDate = value;
    }

}
