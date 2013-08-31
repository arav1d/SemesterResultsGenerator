package com.buybazaar;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class GoodsMusterFrame  extends JFrame implements ActionListener{
	
	
	
	public GoodsMusterFrame() throws MalformedURLException
	{
		System.setProperty("java.net.useSystemProxies", "true");
		
		Container c=getContentPane();//getting the content pane of the frame
		Box box=Box.createVerticalBox();//creating a vertical box and adding it to the frame
		c.add(box);
		pack();
		
		//creating a panel for the title
		JPanel titlepanel=new JPanel();
		box.add(titlepanel);
		
		JLabel title=new JLabel("Semester Results Generator v1.0");
		title.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		title.setForeground(new Color(153,0,0));
		titlepanel.add(title);
		titlepanel.add(title);
		//creating a button panel for all the butttons
		JPanel buttonpanel=new JPanel();
		GridBagLayout gbag=new GridBagLayout();
		buttonpanel.setLayout(gbag);
		box.add(buttonpanel);
		
		//constraints
		GridBagConstraints cons=new GridBagConstraints();
		cons.anchor=GridBagConstraints.LINE_START;
		cons.fill=GridBagConstraints.NONE;
		Insets insets=new Insets(2,2,2,2);
		cons.insets=insets;
		cons.gridx=0;
		cons.gridy=0;
		//creating labels
		JLabel createlabel=new JLabel("Click here to create a new template");
		gbag.setConstraints(createlabel, cons);
		buttonpanel.add(createlabel);
	
		cons.gridx=0;
		cons.gridy=1;
		JLabel modifylabel=new JLabel("Click here to modify an existing template");
		gbag.setConstraints(modifylabel, cons);
		buttonpanel.add(modifylabel);
		
		cons.gridx=0;
		cons.gridy=2;
		JLabel gatherlabel=new JLabel("Click here to start gathering product information");
		gbag.setConstraints(gatherlabel, cons);
		buttonpanel.add(gatherlabel);
		
		JButton create=new JButton("Create XML Template");
		create.setCursor(new Cursor(Cursor.HAND_CURSOR));
		create.addActionListener(this);
		create.setActionCommand("create");
		cons.gridx=1;
		cons.gridy=0;
		gbag.setConstraints(create, cons);
		create.setPreferredSize(new Dimension(150,25));
		buttonpanel.add(create);
		
		JButton modify=new JButton("Modify XML Template");
		modify.setCursor(new Cursor(Cursor.HAND_CURSOR));
		modify.setActionCommand("modify");
		modify.addActionListener(this);
		cons.gridx=1;
		cons.gridy=1;
		gbag.setConstraints(modify, cons);
		modify.setPreferredSize(new Dimension(150,25));
		buttonpanel.add(modify);
		
		
		JButton gather=new JButton("Start Collecting Data");
		gather.setCursor(new Cursor(Cursor.HAND_CURSOR));
		gather.addActionListener(this);
		gather.setActionCommand("gather");
		cons.gridx=1;
		cons.gridy=2;
		gbag.setConstraints(gather, cons);
		gather.setPreferredSize(new Dimension(150,25));
		buttonpanel.add(gather);
		
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("create"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
			try
			{  UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
				CreateTemplateFrame f=new CreateTemplateFrame();
				f.setTitle("Creating a new XML template");
				f.setSize(750,500);
				f.setVisible(true);
				f.setResizable(false);
				f.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.gif"));
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			catch (Exception e1)
			{
			e1.printStackTrace();
			}
				}
			}).start();
		}

		else if(e.getActionCommand().equals("modify"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
			try
			{   UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
				ModifyTemplateFrame f=new ModifyTemplateFrame();
				f.setTitle("Modifying an XML template");
				f.setSize(750,500);
				f.setVisible(true);
				f.setResizable(false);
				f.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.gif"));
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			catch (Exception e1)
			{
			e1.printStackTrace();
			}
				}
			}).start();
		}
			
		else if(e.getActionCommand().equals("gather"))
		{
			new Thread(new Runnable()
			{
				public void run()
				{
			try
			{UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
				GatherProductDetailsFrame f=new GatherProductDetailsFrame();
				f.setTitle("Gathering the product information");
				f.setSize(750,500);
				f.setVisible(true);
				f.setResizable(false);
				f.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.gif"));
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			catch (Exception e1)
			{
			e1.printStackTrace();
			}
				}
			}).start();
		}
	}

	/**
	 * @param args
	 * @throws MalformedURLException 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// TODO Auto-generated method stub
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
	GoodsMusterFrame f=new GoodsMusterFrame();
			f.setTitle("Semester Results Generator");
			f.setSize(450,300);
			f.setResizable(false);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.gif"));

	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
