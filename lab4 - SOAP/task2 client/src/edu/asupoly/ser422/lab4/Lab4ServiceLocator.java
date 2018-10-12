/**
 * Lab4ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.asupoly.ser422.lab4;

public class Lab4ServiceLocator extends org.apache.axis.client.Service implements edu.asupoly.ser422.lab4.Lab4Service {

    public Lab4ServiceLocator() {
    }


    public Lab4ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Lab4ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Lab4ServiceHttpSoap11Endpoint
    private java.lang.String Lab4ServiceHttpSoap11Endpoint_address = "http://localhost:8080/axis2/services/Lab4Service";

    public java.lang.String getLab4ServiceHttpSoap11EndpointAddress() {
        return Lab4ServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Lab4ServiceHttpSoap11EndpointWSDDServiceName = "Lab4ServiceHttpSoap11Endpoint";

    public java.lang.String getLab4ServiceHttpSoap11EndpointWSDDServiceName() {
        return Lab4ServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setLab4ServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        Lab4ServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public edu.asupoly.ser422.lab4.Lab4ServicePortType getLab4ServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Lab4ServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLab4ServiceHttpSoap11Endpoint(endpoint);
    }

    public edu.asupoly.ser422.lab4.Lab4ServicePortType getLab4ServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            edu.asupoly.ser422.lab4.Lab4ServiceSoap11BindingStub _stub = new edu.asupoly.ser422.lab4.Lab4ServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getLab4ServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLab4ServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        Lab4ServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (edu.asupoly.ser422.lab4.Lab4ServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                edu.asupoly.ser422.lab4.Lab4ServiceSoap11BindingStub _stub = new edu.asupoly.ser422.lab4.Lab4ServiceSoap11BindingStub(new java.net.URL(Lab4ServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getLab4ServiceHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Lab4ServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getLab4ServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://lab4.ser422.asupoly.edu", "Lab4Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://lab4.ser422.asupoly.edu", "Lab4ServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Lab4ServiceHttpSoap11Endpoint".equals(portName)) {
            setLab4ServiceHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
