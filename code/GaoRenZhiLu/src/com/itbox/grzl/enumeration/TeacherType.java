package com.itbox.grzl.enumeration;
/**
 * 
 * @author youzh
 *
 */
public enum TeacherType {
    Pro(1, "专业导师"), Peo(2, "人力导师");
    
    private int id;
    private String name;
    
	private TeacherType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    public static String getTeacherName(int id) {
    	for (TeacherType name : TeacherType.values()) {
			if (name.id == id) {
				return name.name;
			}
		}
		return "";
    }
    
    public static int getTeacherId(int id) {
    	for (TeacherType teacherId : TeacherType.values()) {
    		if (teacherId.id == id) {
    			return teacherId.id;
    		}
    	}
    	return 0;
    }
    
    public static String[] getAllTeacherName() {
    	TeacherType [] values = TeacherType.values();
    	String [] teachersName = new String[values.length];
    	for (int i = 0; i < teachersName.length; i++) {
    		teachersName[i] = values[i].getName();
		}
		return teachersName;
    }
}
