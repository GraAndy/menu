package com.graandy.forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CategoryForm {

	protected Shell shell;
	private Text text;
	Connection conn = null;
	Statement stmp = null;
	ResultSet rs = null;
	private Text text_1;

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CategoryForm window = new CategoryForm();
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
		shell.setSize(1280, 720);
		shell.setText("Категории");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(67, 50, 163, 20);
		lblNewLabel.setText("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0438");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(67, 91, 143, 26);
		final Combo combo = new Combo(shell, SWT.READ_ONLY);
		combo.setBounds(67, 288, 143, 28);
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try{
					Class.forName("org.h2.Driver");
					conn = DriverManager.getConnection("jdbc:h2:file://C:\\Java\\menu;MV_STORE=FALSE", "sa", "");
					stmp = conn.createStatement();
					conn.setAutoCommit(false);
					try{
					String sql = "INSERT INTO category (category_name) VALUES (?)";
			        PreparedStatement stmt = conn.prepareStatement(sql);
			        stmt.setString(1, text.getText());
			        stmt.execute();
			        conn.commit();		}
					catch (SQLException se){
						MessageBox mb = new MessageBox(shell);
						mb.setMessage("Ошибка в запросе");
						mb.open();
					}
					
				}
				catch(SQLException | ClassNotFoundException e4){
					MessageBox mb = new MessageBox(shell);
					mb.setMessage("Ошибка подключения к БД");
					mb.open();
					e4.printStackTrace();
				}
				finally{
					if (conn!= null)
						try {
							MessageBox mb = new MessageBox(shell);
							mb.setMessage("Добавление прошло успешно");
							mb.open();
							conn.close();
							comboUpdate(combo);
						} catch (SQLException e4) {
							// TODO Auto-generated catch block
							MessageBox mb = new MessageBox(shell);
							mb.setMessage("Соединение уже закрыто");
							mb.open();
						}
				}	
				
			}
				
			
		});
		btnNewButton.setBounds(67, 146, 90, 30);
		btnNewButton.setText("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		

		
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0438");
		label.setBounds(67, 253, 163, 20);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(67, 364, 143, 26);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					Class.forName("org.h2.Driver");
					conn = DriverManager.getConnection("jdbc:h2:file://C:\\Java\\menu;MV_STORE=FALSE", "sa", "");
				
					conn.setAutoCommit(false);		
					String sql = "select * from category where category_name = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, combo.getText());
					rs = stmt.executeQuery();
					String id = "";
					while(rs.next())		
						id = rs.getString("id");
					conn.commit();	
					sql = "insert into subcategory (subcategory_name, id_category) VALUES (?, ?)";
					PreparedStatement stmt2 = conn.prepareStatement(sql);
					stmt2.setString(1, text_1.getText());
					stmt2.setString(2, id);
					stmt2.execute();
			        conn.commit();	
			        MessageBox mb = new MessageBox(shell);
					mb.setMessage("Добавление прошло успешно");
					mb.open();
				}
				catch(SQLException | ClassNotFoundException e6){
					MessageBox mb = new MessageBox(shell);
					mb.setMessage("Ошибка в запросе" + e6.toString());
					mb.open();
					
				}
				finally{
					if (conn!= null)
						try {
							conn.close();
						} catch (SQLException e5) {
							// TODO Auto-generated catch block
							MessageBox mb = new MessageBox(shell);
							mb.setMessage("Соединение уже закрыто");
							mb.open();
						}
				}	
				
			}
		});
		button.setBounds(67, 420, 90, 30);
		button.setText("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(67, 334, 180, 20);
		label_1.setText("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043F\u043E\u0434\u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0438");
		
		comboUpdate(combo);

		
		

	}
	private void comboUpdate(Combo combo){
		ArrayList<String> arrName = new ArrayList<String>();
		try{
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:file://C:\\Java\\menu;MV_STORE=FALSE", "sa", "");
			stmp = conn.createStatement();
			conn.setAutoCommit(false);		
			String sql = "select * from category";
			rs = stmp.executeQuery(sql);
			while(rs.next())		
				arrName.add(rs.getString("category_name"));
			String[] ed = (String[]) arrName.toArray(new String[arrName.size()]);
			combo.setItems(ed);
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
