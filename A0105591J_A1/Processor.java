import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;


public class Processor extends ApplicationWindow {
	private Text titleText;
	private Text wordText;

	/**
	 * Create the application window.
	 */
	public Processor() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		titleText = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		titleText.setBounds(20, 39, 176, 203);
		
		Label lblTitles = new Label(container, SWT.NONE);
		lblTitles.setBounds(20, 16, 61, 17);
		lblTitles.setText("Titles:");
		
		wordText = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		wordText.setBounds(229, 39, 120, 203);
		
		Label lblIgnoredWords = new Label(container, SWT.NONE);
		lblIgnoredWords.setBounds(229, 16, 97, 17);
		lblIgnoredWords.setText("Ignored Words:");
		
		final Label lblResults = new Label(container, SWT.WRAP);
		lblResults.setBounds(385, 16, 80, 17);
		lblResults.setText("Results:");
		
		final StyledText Resulttext = new StyledText(container, SWT.BORDER |SWT.V_SCROLL|SWT.H_SCROLL);
		Resulttext.setBounds(385, 39, 189, 203);
		
		Button ProcessButton = new Button(container, SWT.NONE);
		ProcessButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//get Input
				InputStream is = new ByteArrayInputStream(titleText.getText().getBytes()); 
				// read it with BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				List <String> titles = new ArrayList <String>();
				List <String> nos = new ArrayList <String>();
				String cur=null;
				try{
				while ((cur=br.readLine())!=null)
				{
					titles.add(cur.trim());
				}
				
				}
				catch (IOException x)
				{
					MessageDialog.openError(getShell(), "Error", "There is an IOexception");
					return;
				}
				
				
				is = new ByteArrayInputStream(wordText.getText().getBytes());
				 
			    Scanner sc = new Scanner(new InputStreamReader(is));
			  
				while (sc.hasNext())
				{
					nos.add(sc.next());
				}
				sc.close();
		
				
				//Start Processing
				circShifter myShifter= new circShifter (titles,nos);
				myShifter.doShift();
				myShifter.alphabeter();
				
				//End processing, Output result
				String result=myShifter.print();
				Resulttext.setText(result);
				
				//Apply the bold effect
				int start=0;
				int currentl;
				StyleRange styleRange=null;
				ArrayList<StyleRange> tmp = new ArrayList <StyleRange>();
				
				
				while (start<result.length())
				{
					//System.out.println(start);
					//System.out.println(result.length());
				    currentl=result.substring(start).indexOf("\n");	
					styleRange = new StyleRange();
					styleRange.start = start;
					styleRange.length = result.substring(start).indexOf(" ");
					styleRange.fontStyle = SWT.BOLD;
					tmp.add(styleRange);
					
					start+=currentl+1;
					
				}
				
				StyleRange [] rs=new StyleRange[tmp.size()];
				for(int i=0;i<tmp.size();i++)
					rs[i]=tmp.get(i);
				Resulttext.setStyleRanges(rs);
				
				
				
				
			}
		});
		ProcessButton.setBounds(20, 257, 80, 27);
		ProcessButton.setText("CircularShift");
		
	

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Processor window = new Processor();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("KWIC Processor");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(611, 408);
	}
}
