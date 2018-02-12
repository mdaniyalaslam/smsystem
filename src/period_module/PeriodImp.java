package period_module;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import entity.Classes;
import entity.Period;
import entity.var;

public class PeriodImp implements PeriodModule {
	static List<Classes> classList = new ArrayList<Classes>();

	@Override
	public int insertPeriod(int id, String name, int terms, int tests, Date from, Date to, int userid, String status,
			String value) {
		int i = 0;
		try {
			System.out.println(id + name + terms + tests + from + to + userid + status + value);
			var.cst = var.conn.prepareCall("{? = call zk.add_period(?,?,?,?,?,?,?,?,?)}");
			var.cst.registerOutParameter(++i, Types.NUMERIC);
			var.cst.setInt(++i, id);
			var.cst.setString(++i, name);
			var.cst.setInt(++i, terms);
			var.cst.setInt(++i, tests);
			var.cst.setDate(++i, new java.sql.Date(from.getTime()));
			var.cst.setDate(++i, new java.sql.Date(to.getTime()));
			var.cst.setString(++i, status);
			var.cst.setInt(++i, userid);
			var.cst.setString(++i, value);
			var.cst.execute();
			return Integer.parseInt(var.cst.getObject(1).toString());

			// Clients.showNotification(cst.getObject(1).toString());
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public List<Period> getPeriod() {
		List<Period> period = new ArrayList<Period>();
		try {

			var.stmt = var.conn.prepareStatement("select * from zk.get_period()");
			var.rs = var.stmt.executeQuery();
			while (var.rs.next()) {
				period.add(new Period(var.rs.getInt(1), var.rs.getString(2), var.rs.getInt(3), var.rs.getInt(4),
						var.rs.getDate(5), var.rs.getDate(6), var.rs.getInt(11), var.rs.getString(9),
						var.rs.getString(14)));
			}
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}

		return period;
	}

	@Override
	public void deletePeriod(Period period) {
		try {
			System.out.println(period.getId());
			var.stmt = var.conn.prepareStatement("delete from zk.schx_period where schx_period_id=" + period.getId());
			var.stmt.execute();
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}

	}

	@Override
	public void deletePeriodDetail(Period period) {
		try {
			var.stmt = var.conn.prepareStatement(
					"delete from zk.schx_period_detail where schx_period_detail_id=" + period.getId());
			var.stmt.execute();
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}

	}

	@Override
	public List<Period> getPeriodDetail(int pid) {
		List<Period> period = new ArrayList<Period>();
		try {

			var.stmt = var.conn.prepareStatement("select * from zk.get_period_detail(" + pid + ") ");
			var.rs = var.stmt.executeQuery();
			while (var.rs.next()) {
				period.add(new Period(var.rs.getInt(1), var.rs.getInt(2), var.rs.getString(3), var.rs.getInt(8),
						var.rs.getString(6), var.rs.getString(11)));
			}
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}

		return period;
	}

	@Override
	public int insertPeriodDetail(int pdetail_id, int pid, String month, int userid, String ischeck, String key) {
		int i = 0;
		try {
			var.cst = var.conn.prepareCall("{? = call zk.add_period_detail(?,?,?,?,?,?)}");
			var.cst.registerOutParameter(++i, Types.NUMERIC);
			var.cst.setInt(++i, pdetail_id);
			var.cst.setInt(++i, pid);
			var.cst.setString(++i, month);
			var.cst.setString(++i, ischeck);
			var.cst.setInt(++i, userid);
			var.cst.setString(++i, key);
			var.cst.execute();
			return Integer.parseInt(var.cst.getObject(1).toString());

			// Clients.showNotification(cst.getObject(1).toString());
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	public List<String> getMonths(int pid) {
		List<String> months = new ArrayList<String>();
		try {

			var.stmt = var.conn.prepareStatement("select zk.get_months("+ pid + ")");
			var.rs = var.stmt.executeQuery();
			while (var.rs.next()) {
				months.add(var.rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println("wahaj exception " + e);
		}

		return months;
	}
}
