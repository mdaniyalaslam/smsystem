/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package sidebar;

import services.SidebarPage;
import services.SidebarPageConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class SidebarAjaxbasedController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	Navbar navbar;

	// wire service
	SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		// to initial view after view constructed.
		// Rows rows = navbar.get;
		int i = 0;
		ArrayList<String> pageHead = new ArrayList<String>();
		pageHead.add("Setups");
		pageHead.add("Transactions");
		pageHead.add("Reports");
		for (ArrayList<SidebarPage> page : pageConfig.getPages()) {
			Nav nav = new Nav();
			Integer sz = page.size();
			nav.setBadgeText(sz.toString());
			nav.setLabel(pageHead.get(i++));
			for (SidebarPage childpage : page) {
				Navitem navitem = constructSidebarRow(childpage.getLabel(), childpage.getIconUri(),
						childpage.getUri());
				nav.appendChild(navitem);
			}
			navbar.appendChild(nav);

		}
	}

	private Navitem constructSidebarRow(final String label, String imageSrc, final String locationUri) {

		// construct component and hierarchy

		Navitem navitem = new Navitem();
		// Image image = new Image(imageSrc);

		navitem.setLabel(label);
		// set style attribute
		navitem.setSclass("sidebar-fn");

		// new and register listener for events
		EventListener<Event> onActionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				// redirect current url to new location
				if (locationUri.startsWith("http")) {
					// open a new browser tab
					Executions.getCurrent().sendRedirect(locationUri);
				} else {
					// use iterable to find the first include only
					Include include = (Include) Selectors.iterable(navbar.getPage(), "#mainInclude").iterator().next();
					include.setSrc(locationUri);

					// advance bookmark control,
					// //bookmark with a prefix
					if (label != null) {
						getPage().getDesktop().setBookmark("p_" + label);
					}
				}
			}
		};
		navitem.addEventListener(Events.ON_CLICK, onActionListener);

		return navitem;
	}

}
