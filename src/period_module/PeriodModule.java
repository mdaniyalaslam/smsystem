package period_module;

import java.util.Date;
import java.util.List;

import entity.Period;

public interface PeriodModule {

	void deletePeriod(Period period);

	List<Period> getPeriod();

	int insertPeriod(int id, String name, int terms, int tests, Date date, Date date2, int userid, String status,
			String value);

	int insertPeriodDetail(int pdetail_id, int pid, String month, int userid, String ischeck, String key);

	void deletePeriodDetail(Period period);

	List<Period> getPeriodDetail(int pid);
	
	List<String> getMonths(int pid);
}
