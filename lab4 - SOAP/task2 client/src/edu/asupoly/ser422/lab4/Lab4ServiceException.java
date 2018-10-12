/**
 * Lab4ServiceException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.asupoly.ser422.lab4;

public class Lab4ServiceException  implements java.io.Serializable {
    private edu.asupoly.ser422.lab4.Exception lab4ServiceException;

    public Lab4ServiceException() {
    }

    public Lab4ServiceException(
           edu.asupoly.ser422.lab4.Exception lab4ServiceException) {
           this.lab4ServiceException = lab4ServiceException;
    }


    /**
     * Gets the lab4ServiceException value for this Lab4ServiceException.
     * 
     * @return lab4ServiceException
     */
    public edu.asupoly.ser422.lab4.Exception getLab4ServiceException() {
        return lab4ServiceException;
    }


    /**
     * Sets the lab4ServiceException value for this Lab4ServiceException.
     * 
     * @param lab4ServiceException
     */
    public void setLab4ServiceException(edu.asupoly.ser422.lab4.Exception lab4ServiceException) {
        this.lab4ServiceException = lab4ServiceException;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Lab4ServiceException)) return false;
        Lab4ServiceException other = (Lab4ServiceException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lab4ServiceException==null && other.getLab4ServiceException()==null) || 
             (this.lab4ServiceException!=null &&
              this.lab4ServiceException.equals(other.getLab4ServiceException())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLab4ServiceException() != null) {
            _hashCode += getLab4ServiceException().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Lab4ServiceException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://lab4.ser422.asupoly.edu", ">Lab4ServiceException"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lab4ServiceException");
        elemField.setXmlName(new javax.xml.namespace.QName("http://lab4.ser422.asupoly.edu", "Lab4ServiceException"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://lab4.ser422.asupoly.edu", "Exception"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
