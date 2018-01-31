package edu.upf.taln.onto.mode_selection;

public class UserInfo {
	
	private final String name;
	private final String gender; 
    private Integer age;

    public UserInfo(String name, String gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public UserInfo(String name, String gender) {
        this(name, gender, null);
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
}
