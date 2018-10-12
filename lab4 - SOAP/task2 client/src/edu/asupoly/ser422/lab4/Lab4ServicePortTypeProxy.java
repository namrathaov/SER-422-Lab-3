package edu.asupoly.ser422.lab4;

public class Lab4ServicePortTypeProxy implements edu.asupoly.ser422.lab4.Lab4ServicePortType {
  private String _endpoint = null;
  private edu.asupoly.ser422.lab4.Lab4ServicePortType lab4ServicePortType = null;
  
  public Lab4ServicePortTypeProxy() {
    _initLab4ServicePortTypeProxy();
  }
  
  public Lab4ServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initLab4ServicePortTypeProxy();
  }
  
  private void _initLab4ServicePortTypeProxy() {
    try {
      lab4ServicePortType = (new edu.asupoly.ser422.lab4.Lab4ServiceLocator()).getLab4ServiceHttpSoap11Endpoint();
      if (lab4ServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)lab4ServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)lab4ServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (lab4ServicePortType != null)
      ((javax.xml.rpc.Stub)lab4ServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public edu.asupoly.ser422.lab4.Lab4ServicePortType getLab4ServicePortType() {
    if (lab4ServicePortType == null)
      _initLab4ServicePortTypeProxy();
    return lab4ServicePortType;
  }
  
  public java.lang.String[] getSubjects() throws java.rmi.RemoteException{
    if (lab4ServicePortType == null)
      _initLab4ServicePortTypeProxy();
    return lab4ServicePortType.getSubjects();
  }
  
  public double calculateGrade(java.lang.String args0, java.lang.String args1) throws java.rmi.RemoteException{
    if (lab4ServicePortType == null)
      _initLab4ServicePortTypeProxy();
    return lab4ServicePortType.calculateGrade(args0, args1);
  }
  
  public java.lang.String mapToLetterGrade(double args0) throws java.rmi.RemoteException{
    if (lab4ServicePortType == null)
      _initLab4ServicePortTypeProxy();
    return lab4ServicePortType.mapToLetterGrade(args0);
  }
  
  public java.lang.String[] getService() throws java.rmi.RemoteException{
    if (lab4ServicePortType == null)
      _initLab4ServicePortTypeProxy();
    return lab4ServicePortType.getService();
  }
  
  
}