package com.graandy.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MainForm {

	protected Shell shell;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainForm window = new MainForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Menu");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setText("\u0421\u0435\u0440\u0432\u0438\u0441");
		
		Menu menu_1 = new Menu(menuItem);
		menuItem.setMenu(menu_1);
		
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {		
				try {
					
					CreateForm window2 = new CreateForm();
					
					window2.open();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			
			}
		});
		menuItem_1.setSelection(true);
		menuItem_1.setText("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0431\u043B\u044E\u0434\u043E");
		
		MenuItem menuItem_2 = new MenuItem(menu_1, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
	try {
					
					CategoryForm window3 = new CategoryForm();
					
					window3.open();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		menuItem_2.setText("\u041A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0438");

	}
}
