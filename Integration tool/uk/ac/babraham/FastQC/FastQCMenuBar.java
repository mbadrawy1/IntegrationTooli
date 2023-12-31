/**
 * Copyright Copyright 2010-17 Simon Andrews
 *
 *    This file is part of FastQC.
 *
 *    FastQC is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    FastQC is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with FastQC; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package uk.ac.babraham.FastQC;

import uk.ac.babraham.FastQC.Dialogs.AboutDialog;
import uk.ac.babraham.FastQC.Help.HelpDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class FastQCMenuBar extends JMenuBar implements ActionListener {

	private FastQCApplication application;
	
	public FastQCMenuBar (FastQCApplication application) {
		this.application = application;
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem fileOpen = new JMenuItem("Open...");
		fileOpen.setMnemonic(KeyEvent.VK_O);
		fileOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileOpen.setActionCommand("open");
		fileOpen.addActionListener(this);
		fileMenu.add(fileOpen);
		
		fileMenu.addSeparator();
		
		JMenuItem fileSave = new JMenuItem("Save report...");
		fileSave.setMnemonic(KeyEvent.VK_S);
		fileSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileSave.setActionCommand("save");
		fileSave.addActionListener(this);
		fileMenu.add(fileSave);
		
		fileMenu.addSeparator();
		
		JMenuItem fileClose = new JMenuItem("Close");
		fileClose.setMnemonic(KeyEvent.VK_C);
		fileClose.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileClose.setActionCommand("close");
		fileClose.addActionListener(this);
		fileMenu.add(fileClose);
		

		JMenuItem fileCloseAll = new JMenuItem("Close All");
		fileCloseAll.setMnemonic(KeyEvent.VK_A);
		fileCloseAll.setActionCommand("close_all");
		fileCloseAll.addActionListener(this);
		fileMenu.add(fileCloseAll);

		
		fileMenu.addSeparator();

		JMenuItem openTrimmomatic = new JMenuItem("Open Trimmomatic");
		openTrimmomatic.setMnemonic(KeyEvent.VK_T);
		openTrimmomatic.setActionCommand("open_trimmomatic");
		openTrimmomatic.addActionListener(this);
		fileMenu.add(openTrimmomatic);
		
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.setMnemonic(KeyEvent.VK_X);
		fileExit.setActionCommand("exit");
		fileExit.addActionListener(this);
		fileMenu.add(fileExit);
		
		add(fileMenu);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem helpContents = new JMenuItem("Contents...");
		helpContents.setMnemonic(KeyEvent.VK_C);
		helpContents.setActionCommand("help_contents");
		helpContents.addActionListener(this);
		helpMenu.add(helpContents);
		
		helpMenu.addSeparator();
		
		JMenuItem helpAbout = new JMenuItem("About FastQC");
		helpAbout.setMnemonic(KeyEvent.VK_A);
		helpAbout.setActionCommand("about");
		helpAbout.addActionListener(this);
		
		helpMenu.add(helpAbout);
		
		add(helpMenu);
		
	}

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		
		if (command.equals("exit")) {
			System.exit(0);
		}
		else if (command.equals("open")) {
			application.openFile();
		}
		else if (command.equals("save")) {
			application.saveReport();
		}
		else if (command.equals("close")) {
			application.close();
		}
		else if (command.equals("close_all")) {
			application.closeAll();
		}
		else if (command.equals("help_contents")) {
			try {
				new HelpDialog(application,new File(URLDecoder.decode(ClassLoader.getSystemResource("Help").getFile(),"UTF-8")));
			} 
			catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		else if (command.equals("about")) {
			new AboutDialog(application);
		}else if (command.equals("open_trimmomatic")) {
			// Display usage instructions
			JOptionPane.showMessageDialog(application, "To use Trimmomatic, the terminal will open. Navigate to the directory containing the Trimmomatic .jar file.\n\n You should type cd Trimmomatic-0.39 first\n"
					+ "Then run the following command:\n\n"
					+ "java -jar trimmomatic-0.39.jar [options]\n\n"
					+ "Replace [options] with your Trimmomatic options and input files.", "Trimmomatic Usage", JOptionPane.INFORMATION_MESSAGE);


			try {
				Runtime.getRuntime().exec("cmd.exe /c start");
				//Process process = Runtime.getRuntime().exec("cmd.exe /c start");

//				OutputStreamWriter osw = new OutputStreamWriter(process.getOutputStream());
//				osw.write("cd " + specificDirectory + "\n"); // Change to the specific directory
//				osw.flush();
//				osw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		else {
			JOptionPane.showMessageDialog(application, "Unknown menu command "+command, "Unknown command", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
