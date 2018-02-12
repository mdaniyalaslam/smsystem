package period_module;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import entity.Period;
import sm.UserInfoServiceImpl;

public class PeriodController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	Component form, data;
	@Wire
	Button submit_ap;
	@Wire
	Checkbox status;
	@Wire
	Include ap;
	@Wire
	Textbox key;
	@Wire
	Intbox name, terms, tests;
	@Wire
	Datebox from, to;
	@Wire
	Listbox periodListbox;
	private int period_id = 0;
	Intbox pid;
	Tabbox tbx;
	Combobox pdmonth;
	Div detailData, detailForm;
	Tab tab;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		tbx = (Tabbox) ap.getParent().getParent().getParent();
		tab = (Tab) tbx.getFellow("tab_pd");
		// pid=(Intbox)pd.getFellow("pid");
		tab.setDisabled(true);
		// id.setLabel("0");
	}

	private PeriodModule periodImp = new PeriodImp();

	@Listen("onClick=#submit_ap;")
	public void periodSubmit() {
		Clients.showBusy(form, "Please Wiat ...");
		if (from.getValue().after(to.getValue()) || from.getValue().equals(to.getValue())) {
			Clients.clearBusy(form);
			Clients.alert("Enter Correct Date Please! Thank You");
		} else {
			String ischeck = null;
			if (status.isChecked()) {
				ischeck = "Y";
			} else {
				ischeck = "N";
			}
			int result = periodImp.insertPeriod(period_id, name.getValue().toString(), terms.getValue(),
					tests.getValue(), from.getValue(), to.getValue(), UserInfoServiceImpl.userid, ischeck,
					key.getValue());
			if (result == 0) {
				
				Clients.showNotification("Error", "info", form, "middle_center", 1500);
				
			} else if (result == 3) {
				Clients.showNotification("Period Already Entered", "error", name, "end_center", 2000);
				name.focus();
			} else {

				pid = (Intbox) tbx.getFellow("pd").getFellow("pid");
				tbx.setSelectedTab(tab);
				tab.setDisabled(false);
				pid.setValue(result);
				Clients.showNotification(((result == period_id) ? "Data Updated" : "Data Inserted ") + " Succesfully.",
						"info", (Include) tbx.getFellow("pd"), "middle_center", 1500);
				detailData = (Div) tbx.getFellow("pd").getFellow("data");
				period_id = result;
				detailForm = (Div) tbx.getFellow("pd").getFellow("form");
				detailData.setVisible(false);
				detailForm.setVisible(true);
				pdmonth = (Combobox) tbx.getFellow("pd").getFellow("pdmonth");
				List<String> months = periodImp.getMonths(pid.getValue());
				pdmonth.setModel(new ListModelList<String>(months));
			}
			Clients.clearBusy(form);

		}
	}

	@Listen("onPeriodEdit = #periodListbox")
	public void doPeriodEdit(ForwardEvent evt) {
		// System.out.println(evt);
		System.out.println(evt.getOrigin());
		Listitem litem = null;
		if (evt.getOrigin().getTarget().isListenerAvailable("onClick", true)) {
			Button btn = (Button) evt.getOrigin().getTarget();
			litem = (Listitem) btn.getParent().getParent();

		} else {
			Listbox listbox = (Listbox) evt.getOrigin().getTarget();
			litem = (Listitem) listbox.getSelectedItem();
		}

		Period period = (Period) litem.getValue();
		this.fetch();
		period_id = period.getId();
		name.setText(period.getName());
		terms.setValue(period.getTerms());
		tests.setValue(period.getTests());
		from.setValue(period.getFrom());
		to.setValue(period.getTo());
		key.setText(period.getValue());

		if (period.getStatus().equals("Y")) {
			status.setChecked(true);
		} else {
			status.setChecked(false);
		}
		tab.setDisabled(false);
		pid = (Intbox) tbx.getFellow("pd").getFellow("pid");
		pid.setValue(period_id);
		detailData = (Div) tbx.getFellow("pd").getFellow("data");
		detailForm = (Div) tbx.getFellow("pd").getFellow("form");
		detailData.setVisible(false);
		detailForm.setVisible(true);

		pdmonth = (Combobox) tbx.getFellow("pd").getFellow("pdmonth");
		List<String> months = periodImp.getMonths(pid.getValue());
		pdmonth.setModel(new ListModelList<String>(months));

	}

	@Listen("onPeriodDelete = #periodListbox")
	public void doTodoDelete(ForwardEvent evt) {
		System.out.println("hello 2 ");
		Button btn = (Button) evt.getOrigin().getTarget();
		Listitem litem = (Listitem) btn.getParent().getParent();
		Period period = (Period) litem.getValue();
		litem.detach();
		periodImp.deletePeriod(period);
		Clients.showNotification("Data deleted succesfully.", "info", data, "middle_center", 1500);
		// Clients.showN
	}

	@Listen("onClick=#toggle")
	public void fetch() {
		if (!data.isVisible()) {
			data.setVisible(true);
			form.setVisible(false);
			List<Period> result = periodImp.getPeriod();
			periodListbox.setModel(new ListModelList<Period>(result));
		} else {
			data.setVisible(false);
			form.setVisible(true);
		}
	}

	@Listen("onClick=#New")
	public void fieldsEmpty() {
		tab.setDisabled(true);
		period_id = 0;
		name.setValue(null);
		terms.setValue(null);
		tests.setValue(null);
		from.setValue(null);
		to.setValue(null);
		key.setValue("");
		status.setChecked(false);
		data.setVisible(false);
		form.setVisible(true);
	}
}
