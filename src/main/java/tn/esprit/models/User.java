package tn.esprit.models;

public class User {
    int id,age;
    String name, lastname;

    public User(){
    }
    public User( String name, String lastname,int age) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString(){
        return "User{"+
                "id= "+id+ '\''+
                ", Name= "+ name+ '\''+
                ", lastname= "+lastname+ '\''+
                ", age= "+age + '\''+
                "}\n";
    }
}


