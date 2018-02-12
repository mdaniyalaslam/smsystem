package rep;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkex.zul.Jasperreport;

import entity.var;

public class IReport extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	Component div;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("title", "2013");
		// zul component
//		JasperCompileManager.compileReport("C:\\Users\\Administrator\\Documents\\sms\\src\\periodreport.jrxml");
		Jasperreport report = (Jasperreport) div.getFellow("report");
		report.setDataConnection(var.conn);
		report.setParameters(param);
		report.setSrc("/periodreport.jasper");
	}
}
