package com.graandy.forms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

public class CreateForm {

    Display display;
    protected Shell shell = new Shell(display);
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_4;
	private Text text_5;
	private String img = "";
	Connection conn = null;
	Statement stmp = null;
	ResultSet rs = null;
	FileInputStream fis = null;
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			CreateForm window3 = new CreateForm();
			window3.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		display = Display.getDefault();
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
		shell.setText("Добавление блюда");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(31, 25, 70, 20);
		label.setText("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(31, 51, 375, 26);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(31, 300, 70, 20);
		label_1.setText("\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(31, 344, 375, 160);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(31, 93, 70, 20);
		lblNewLabel.setText("\u0412\u044B\u0445\u043E\u0434");
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(31, 119, 122, 26);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(31, 162, 122, 20);
		label_2.setText("\u041F\u043E\u0434\u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u044F");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(31, 220, 70, 20);
		label_3.setText("\u0426\u0435\u043D\u0430");
		
		text_4 = new Text(shell, SWT.BORDER);
		text_4.setBounds(31, 246, 78, 26);
		
		final Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setBounds(496, 103, 482, 441);
	   
		final Combo combo = new Combo(shell, SWT.READ_ONLY);
		combo.setBounds(31, 186, 122, 28);
		
		text_5 = new Text(shell, SWT.BORDER);
		text_5.setBounds(496, 51, 335, 26);

		
		Button button = new Button(shell, SWT.NONE);
		button.setText("\u0412\u044B\u0431\u0440\u0430\u0442\u044C \u0444\u0430\u0439\u043B");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Image image = null;
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
				String [] filterNames = new String [] {"Image Files", "All Files (*)"};
				String [] filterExtensions = new String [] {"*.gif;*.png;*.xpm;*.jpg;*.jpeg;*.tiff", "*"};
				String filterPath = "/";
				String platform = SWT.getPlatform();
				if (platform.equals("win32") || platform.equals("wpf")) {
					filterNames = new String [] {"Image Files", "All Files (*.*)"};
					filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
					filterPath = "c:\\";
				}
				//String selected = "";
				dialog.setFilterNames (filterNames);
				dialog.setFilterExtensions (filterExtensions);
				dialog.setFilterPath (filterPath);
				dialog.setFileName ("myfile");	
				try {img = dialog.open();
				text_5.setText(img);
				}
				catch (IllegalArgumentException er){}

		        try {
		          image = new Image(display, new FileInputStream(img));
		        
		        } catch (FileNotFoundException e1) {
		          // TODO Auto-generated catch block
					MessageBox mb = new MessageBox(shell);
					mb.setMessage("Файл не найден");
					mb.open();
		        }
				canvas.setBackgroundImage(image);
			
			}
		});
		button.setBounds(843, 49, 135, 30);

		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try{
					Class.forName("org.h2.Driver");
					conn = DriverManager.getConnection("jdbc:h2:file://C:\\Java\\menu;MV_STORE=FALSE", "sa", "");
					stmp = conn.createStatement();
					conn.setAutoCommit(false);
					try{
					String sql = "select * from subcategory where subcategory_name = ?";
					PreparedStatement stmt2 = conn.prepareStatement(sql);
					stmt2.setString(1, combo.getText());
					rs = stmt2.executeQuery();
					String id = "";
					while(rs.next())		
						id = rs.getString("id");
					conn.commit();	
					sql = "INSERT INTO product (product_name, description, output, id_subcategory, price, image, availability) VALUES (?, ?, ?, ?, ?, ?, ?)";
			        PreparedStatement stmt = conn.prepareStatement(sql);
			        stmt.setString(1, text.getText());
			        stmt.setString(2, text_1.getText());
			        stmt.setString(3, text_2.getText());
			        stmt.setString(4, id);
			        stmt.setString(5, text_4.getText());
			        stmt.setString(7, "true");
			        File image = new File(text_5.getText());
			        try {
						fis = new FileInputStream(image);
					} catch (FileNotFoundException e3) {
						MessageBox mb = new MessageBox(shell);
						mb.setMessage("Не могу открыть файл");
						mb.open();
					}
			        stmt.setBinaryStream(6, fis, (int) image.length());
			        stmt.execute();
			        conn.commit();	
					MessageBox mb = new MessageBox(shell);
					mb.setMessage("Добавление прошло успешно");
					mb.open();
			        }
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

							conn.close();
						} catch (SQLException e4) {
							// TODO Auto-generated catch block
							MessageBox mb = new MessageBox(shell);
							mb.setMessage("Соединение уже закрыто");
							mb.open();
						}
				}	
				
			}
		});
		button_1.setBounds(31, 547, 90, 30);

		button_1.setText("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(496, 25, 141, 20);
		lblNewLabel_1.setText("\u0418\u0437\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0438\u0435");
		comboUpdate(combo);

	}
		
		private void comboUpdate(Combo combo)
		{
			ArrayList<String> arrName = new ArrayList<String>();
			try{
				Class.forName("org.h2.Driver");
				conn = DriverManager.getConnection("jdbc:h2:file://C:\\Java\\menu;MV_STORE=FALSE", "sa", "");
				stmp = conn.createStatement();
				conn.setAutoCommit(false);		
				String sql = "select * from subcategory";
				rs = stmp.executeQuery(sql);
				while(rs.next())		
					arrName.add(rs.getString("subcategory_name"));
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
