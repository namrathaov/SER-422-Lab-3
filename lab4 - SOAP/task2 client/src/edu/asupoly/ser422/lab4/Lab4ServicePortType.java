/**
 * Lab4ServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.asupoly.ser422.lab4;

public interface Lab4ServicePortType extends java.rmi.Remote {
    public java.lang.String[] getSubjects() throws java.rmi.RemoteException;
    public double calculateGrade(java.lang.String args0, java.lang.String args1) throws java.rmi.RemoteException;
    public java.lang.String mapToLetterGrade(double args0) throws java.rmi.RemoteException;
    public java.lang.String[] getService() throws java.rmi.RemoteException;
}
