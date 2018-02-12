/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package sm;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import entity.var;
import services.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService,Serializable{
	private static final long serialVersionUID = 1L;
	static protected List<User> userList = new ArrayList<User>();
	public static int userid;
//	Connection conn = null;
//	PreparedStatement stmt = null;
//	ResultSet rs = null;
	
	public UserInfoServiceImpl() {	  
		try {
			Class.forName("org.postgresql.Driver");
			var.conn = DriverManager.getConnection(
					"jdbc:postgresql://ZK/postgres", "postgres",
					"postgres");
		} catch (Exception e) {
			System.out.println("Connection Failed because " + e);
		}
	}
	@Override
	public synchronized User findUser(String email){
		try {
			// load driver and get a database connection
			var.stmt = var.conn.prepareStatement("select * from zk.schx_users where email=?");
			var.stmt.setString(1, email);
			var.rs = var.stmt.executeQuery();
			if (var.rs.next()) {
				userid=var.rs.getInt(1);
				User u = new User(var.rs.getInt(1),var.rs.getString(2),var.rs.getString(3),var.rs.getString(4));
				if(email.equals(u.getEmail())){
					return User.clone(u);				
			}
			}
			} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		} 			
		return null;
	}
	
	public void insertClass() {
		
	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
//	public synchronized User updateUser(User user){
//		int s = userList.size();
//		for(int i=0;i<s;i++){
//			User u = userList.get(i);
//			if(user.getAccount().equals(u.getAccount())){
//				userList.set(i,u = User.clone(user));
//				return u;
//			}
//		}
//		throw new RuntimeException("user not found "+user.getAccount());
//	}
}
