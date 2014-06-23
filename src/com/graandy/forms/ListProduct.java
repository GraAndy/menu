package com.graandy.forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class ListProduct {

	protected Shell shell;
	private int id_category;
	Connection conn = null;
	Statement stmp = null;
	ResultSet rs = null;
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		try {
			ListProduct window = new ListProduct();
			window.open(1, "Example");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open(int num, String category_name) {
		//if (num != 0){
		id_category = num;
		Display display = Display.getDefault();
		createContents(category_name);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		//}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(String category_name) {
		shell = new Shell();
		shell.setSize(1280, 720);
		shell.setText("Список блюд");
		
		final List list = new List(shell, SWT.BORDER);
		list.setBounds(43, 77, 553, 519);
		
		final Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(104, 34, 334, 20);
		lblNewLabel.setText(category_name);
		listUpdate(list);
		

	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void listUpdate(List list){	
		ArrayList<String> arrName = new ArrayList<String>();
		try{
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:file://C:\\Java\\menu;MV_STORE=FALSE", "sa", "");
			conn.setAutoCommit(false);		
			String sql = "select product.product_name from product inner join subcategory on product.id_subcategory = subcategory.id where subcategory.ID_CATEGORY = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id_category);
			rs = stmt.executeQuery();
			while(rs.next())		
				arrName.add(rs.getString("product_name"));
			String[] ed = (String[]) arrName.toArray(new String[arrName.size()]);
			list.setItems(ed);
	        conn.commit();		
		}
		catch(SQLException | ClassNotFoundException e){
			MessageBox mb = new MessageBox(shell);
			mb.setMessage("Не могу открыть файл");
			mb.open();
		}
		finally{
			if (conn!= null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					MessageBox mb = new MessageBox(shell);
					mb.setMessage("Соединение уже закрыто");
					mb.open();
				}
		}	
		
	}
}
