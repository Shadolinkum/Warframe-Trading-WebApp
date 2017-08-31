/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userp;

/**
 *
 * @author Gabriel
 */
public class UserData {
    String fname;
    String lname;
    String email;
    int age;



    public void setFname(String value){
        fname = value;
    }

    public void setLname(String value){
        lname = value;
    }
    
    public void setEmail(String value){
        email = value;
    }
    
    public void setAge(int value){
        age = value;
    }
    
    public String getFname(){
        return fname;
    }

    public String getLname(){
        return lname;
    }
    
    public String getEmail(){
        return email;
    }
    
    public int getAge(){
        return age;
    }
    
    public boolean ageValidate(){
        return getAge() < 18;
    }
    
}
