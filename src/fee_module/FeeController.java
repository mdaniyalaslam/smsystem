package fee_module;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import entity.Classes;

public class FeeController extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Component data, form;
	@Wire
	Listbox detailListbox;
	
	private ListModel<String> countriesModel = new ListModelList<String>(new String[]{"China", "France", "Germany", "Italy", "Japan", "Korea", "Sweden",
            "Taiwan", "United Kingdom", "United States"});

	@Listen("onClick=#toggle")
	public void fetch() {
		if (!data.isVisible()) {
			// sql
			data.setVisible(true);
			form.setVisible(false);
//			List<Classes> result = classImp.getClasses();
//			detailListbox.setModel(new ListModelList<Classes>(result));
		} else {
			data.setVisible(false);
			form.setVisible(true);
		}
	}

	@Listen("onClick=#New")
	public void fieldsEmpty() {
//		class_id = 0;
//		name.setValue("");
//		key.setValue("");
//		status.setChecked(false);
		data.setVisible(false);
		form.setVisible(true);
	}
}
