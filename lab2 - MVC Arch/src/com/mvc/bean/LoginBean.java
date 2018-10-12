package com.mvc.bean;
 
//As I have already told it contains only setters and getters
 
public class LoginBean
 {
 private String userName;
 private String password;
 private String role;
 
public String getUserName() {
 return userName;
 }
public void setUserName(String userName) {
 this.userName = userName;
 }
 public String getPassword() {
 return password;
 }
 public void setPassword(String password) {
 this.password = password;
 }
 public String getRole() {
	 return role;
	 }
	 public void setRole(String role) {
	 this.role = role;
 }
 }