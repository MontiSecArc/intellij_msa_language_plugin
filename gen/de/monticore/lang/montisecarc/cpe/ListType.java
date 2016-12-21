
package de.monticore.lang.montisecarc.cpe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * The ListType complex type defines an element that is used to hold a
 *                 collection of individual items. The required generator section provides information about when the
 *                 definition file was compiled and under what version. Additional elements not part of the CPE namespace
 *                 are allowed and are just skipped by validation. In essence, a dictionary file can contain additional
 *                 information that a user can choose to use or not, but this information is not required to be used or
 *                 understood.
 * 
 * <p>Java-Klasse für ListType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="generator" type="{http://cpe.mitre.org/dictionary/2.0}GeneratorType" minOccurs="0"/>
 *         &lt;element ref="{http://cpe.mitre.org/dictionary/2.0}cpe-item" maxOccurs="unbounded"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListType", namespace = "http://cpe.mitre.org/dictionary/2.0", propOrder = {
    "generator",
    "cpeItem",
    "any"
})
public class ListType {

    @XmlElement(namespace = "http://cpe.mitre.org/dictionary/2.0")
    protected GeneratorType generator;
    @XmlElement(name = "cpe-item", namespace = "http://cpe.mitre.org/dictionary/2.0", required = true)
    protected List<ItemType> cpeItem;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Ruft den Wert der generator-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link GeneratorType }
     *     
     */
    public GeneratorType getGenerator() {
        return generator;
    }

    /**
     * Legt den Wert der generator-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneratorType }
     *     
     */
    public void setGenerator(GeneratorType value) {
        this.generator = value;
    }

    /**
     * Gets the value of the cpeItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cpeItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCpeItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemType }
     * 
     * 
     */
    public List<ItemType> getCpeItem() {
        if (cpeItem == null) {
            cpeItem = new ArrayList<ItemType>();
        }
        return this.cpeItem;
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

}
