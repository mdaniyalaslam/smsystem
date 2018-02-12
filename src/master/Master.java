package master;

import java.util.List;

import entity.Classes;

public interface Master {

	public String insertClass(int id, String name, String status,int userid,String value); 
	public List<Classes> getClasses();
	public void deleteClass(Classes cls);
}
