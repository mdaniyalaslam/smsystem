package master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import entity.Classes;

public class ClassImpl implements Master {
	Connection conn = null;
	PreparedStatement stmt = null;
	CallableStatement cst = null;
	ResultSet rs = null;
	static List<Classes> classList = new ArrayList<Classes>();
	public ClassImpl() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://ZK/postgres", "postgres", "postgres");
		} catch (Exception e) {
			System.out.println("Connection Failed because " + e);
		}
	}

	public String insertClass(int id, String name, String status, int userid, String value) {

		try {
			cst = conn.prepareCall("{? =call zk.add_class(?,?,?,?,?)}");
			cst.registerOutParameter(1, Types.VARCHAR);
			cst.setInt(2, id);
			cst.setString(3, name);
			cst.setString(4, status);
			cst.setInt(5, userid);
			cst.setString(6, value);
			cst.execute();
			return cst.getObject(1).toString();

			// Clients.showNotification(cst.getObject(1).toString());
		} catch (Exception e) {
			return "false arha hai" + e;
		}

	}

	public List<Classes> getClasses() {
		List<Classes> classes = new ArrayList<Classes>();
		try {

			stmt = conn.prepareStatement("select * from zk.get_class()");
			rs = stmt.executeQuery();
			while (rs.next()) {
				classes.add(new Classes(rs.getInt(1),rs.getString(2), rs.getString(5), rs.getInt(7), rs.getString(10)));
			}
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}

		return classes;
	}

	@Override
	public void deleteClass(Classes cls) {
		try {
			System.out.println(cls.getId());
			stmt = conn.prepareStatement("delete from zk.schx_class where schx_class_id="+cls.getId());
			stmt.execute();
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}
		
	}
}
