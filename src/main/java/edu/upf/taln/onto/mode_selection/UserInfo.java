package edu.upf.taln.onto.mode_selection;

public class UserInfo {

    private String name;
    private String gender;
    private String culture;
    private Integer age;

    public UserInfo() {
    }
    
    public UserInfo(String name, String gender, Integer age, String culture) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.culture = culture;
    }

    public UserInfo(String name, String gender, String culture) {
        this(name, gender, null, culture);
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getCountry() {
        return culture;
    }
}
