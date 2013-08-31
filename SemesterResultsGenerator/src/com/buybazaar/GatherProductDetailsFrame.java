package com.buybazaar;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@SuppressWarnings({ "serial" })
public class GatherProductDetailsFrame extends JFrame implements ActionListener
{

JTextField fromtext,inputtext,urltext,temptext;
JTextArea logarea;
JProgressBar pbar;
JButton start,stop;
File dataFile=null;
File templateFile=null;
FileWriter csvfile;
String[][] searchKeys;


public GatherProductDetailsFrame()
{
	Container c=getContentPane();//getting the content pane of the frame
	Box box=Box.createVerticalBox();//creating a vertical box and adding it to the frame
	c.add(box);
	pack();
	
	
	//creating the title panel and adding it to the vertical box
	JPanel titlepanel=new JPanel();
	box.add(titlepanel);

	//creating a label for the title and adding it to the title panel
	JLabel title=new JLabel("Gather data");
	titlepanel.add(title);
	
	//creating a panel to contains all the input text fields and labels and buttons
	JPanel inputPanel=new JPanel();
	GridBagLayout gbag=new GridBagLayout();
	inputPanel.setLayout(gbag);
	box.add(inputPanel);
	
		
		//creating the 3rd input text field label and button
		JLabel inputlabel=new JLabel("Select the Reg No TextFile");
		inputPanel.add(inputlabel);
		inputtext=new JTextField(30);
		inputPanel.add(inputtext);
		JButton choosefilebutton=new JButton("Browse");
		choosefilebutton.addActionListener(this);//adding the action listener to the browse button
		choosefilebutton.setActionCommand("chooseFile");
		inputPanel.add(choosefilebutton);
		
		//creating the 1st input text field label and button
		JLabel urllabel=new JLabel("Enter the url link");
		inputPanel.add(urllabel);
		urltext=new JTextField(30);
		inputPanel.add(urltext);
		
		//creating the 1st input text field label and button
		JLabel templabel=new JLabel("Enter the xml template file path to be used");
		inputPanel.add(templabel);
		temptext=new JTextField(30);
		inputPanel.add(temptext);
		JButton choosetempbutton=new JButton("Browse");
		choosetempbutton.addActionListener(this);//adding the action listener to the browse button
		choosetempbutton.setActionCommand("choose");
		inputPanel.add(choosetempbutton);
		
		//creating the gridbad constraints for the labels
		GridBagConstraints labelcons=new GridBagConstraints();
		labelcons.anchor=GridBagConstraints.LINE_START;
		labelcons.fill=GridBagConstraints.NONE;
		Insets insets=new Insets(2, 2, 2, 2);
		labelcons.insets=insets;
		
		
		labelcons.gridx=0;
		labelcons.gridy=2;
		gbag.setConstraints(inputlabel,labelcons);
	
		labelcons.gridx=0;
		labelcons.gridy=3;
		gbag.setConstraints(urllabel,labelcons);
		
		labelcons.gridx=0;
		labelcons.gridy=4;
		gbag.setConstraints(templabel,labelcons);
	
		labelcons.gridx=2;
		labelcons.gridy=4;
		gbag.setConstraints(choosetempbutton,labelcons);
		
		labelcons.gridx=2;
		labelcons.gridy=2;
		gbag.setConstraints(choosefilebutton,labelcons);
		
		
		
		labelcons.ipadx=300;
		labelcons.gridx=1;
		labelcons.gridy=3;
		gbag.setConstraints(urltext,labelcons);
		labelcons.gridx=1;
		labelcons.gridy=2;
		gbag.setConstraints(inputtext,labelcons);
		labelcons.gridx=1;
		labelcons.gridy=4;
		gbag.setConstraints(temptext,labelcons);

		
//creating a panel for the log display
JPanel logpanel=new JPanel();
box.add(logpanel);

//creating a text field for log area
logarea=new JTextArea(13,65);
logarea.setText("Log Details:\n");
logarea.setEditable(false);
logarea.setLineWrap(true);
logarea.setBackground(Color.LIGHT_GRAY);

//creating a scroll pane for the log area
JScrollPane scrollpane=new JScrollPane(logarea);
scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
logpanel.add(scrollpane);

//creating a panel for the progress bar
JPanel progresspanel=new JPanel();
box.add(progresspanel);

//creating the progress bar and adding it to the panel
pbar=new JProgressBar(0,100);
pbar.setStringPainted(true);
pbar.setBorderPainted(true);
Dimension size=new Dimension(725,25);
pbar.setPreferredSize(size);
progresspanel.add(pbar);

//creating a buttonpanel to contn all the buttons
JPanel buttonpanel=new JPanel();
box.add(buttonpanel);

//creating buttons and adding it to the buttonpanel
JButton start=new JButton("Start Process");
buttonpanel.add(start);
start.setCursor(new Cursor(Cursor.HAND_CURSOR));
start.addActionListener(this);
start.setActionCommand("start");
JButton stop=new JButton("Stop Process");
buttonpanel.add(stop);
stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
stop.addActionListener(this);
stop.setActionCommand("stop");
		
}

@Override
public void actionPerformed(ActionEvent e){
	// TODO Auto-generated method stub

if(e.getActionCommand().equals("start"))
{

	new Thread(new Runnable()
	{
	public void run()
	{	
	if(!inputtext.getText().equals("") && !urltext.getText().equals("") && !temptext.getText().equals(""))
	{
		try 
		{
		String url="";
		
		
		//we shud read all the register no from the text file and have it an array
		FileReader fileReader = new FileReader(inputtext.getText());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        
int i=0;
        for (String regno : lines) //for each url source in the string list
        {
				url=urltext.getText()+regno;//append the reg no to the url string
				
				//creating the file object for the xml template using its path
				File xmlfile=new File(temptext.getText());//get the template file selected
				//getting all the field data in the xml template in a double dimensional string array
				searchKeys=getSearchKeys(xmlfile);//getting template data as searchKey
				
				//create a file object for the target csv file
				File file=new File(xmlfile.getAbsolutePath().substring(0,xmlfile.getAbsolutePath().length()-4)+"data.csv");
				//if the file does not exist then create the file and create the header data from the template
				if(!file.exists())
				{
				file.createNewFile();//create the file first
				csvfile=new FileWriter(file);//get its writer object
				
				for(String[] fields:searchKeys)//for every field in the template add the header data columns in the csv file
				{
					csvfile.append("\""+fields[0]+"\"");
					csvfile.append(",");
				}
				csvfile.append("\n");
				}
				else//if the file already exists then jus get the writer object of it
				{
				csvfile=new FileWriter(file,true);
				}
				
				//now we have our destination csv file ready
				//add the product name data to the 1st column
				
				//now proces the url as per xml template and append the field values separated by commas
				//for every url we need to append log details in the log area
				logarea.append("Processing "+url+"\n\n");

				Document doc=getCleanHtmlDocument(url);// get the document object for the html doc belonging to the url

					for (String[] rows : searchKeys)//for every row in the template ie for every field
					{
						List<Node> nodes = getNodes(rows[1], rows[4], doc);//getting all the nodes that match field type and keywords
						csvfile.append("\"");
						for(Node node:nodes)//for each node in that list
						{
						Node sibling = node.getNextSibling();//getting the node's sibling
						csvfile.append(sibling.getTextContent().trim()+" ");//append the field value
						}
						csvfile.append("\"");
						csvfile.append(",");//append a comma after every field value

					}
					csvfile.append("\n"); //append a new line after every url been processed
					//after every url we need to set the progress bar
					i++;
					pbar.setValue((i) * 100 / lines.size());
					csvfile.flush();//flushing the file
					csvfile.close();//closing the file
			}//end of for loop for every url
	   		JOptionPane.showMessageDialog(com.buybazaar.GatherProductDetailsFrame.this,"Processing Complete");
		} 
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (ParserConfigurationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	
	
	else
	{
		JOptionPane.showMessageDialog(null,"Please fill in all the text fields");
	}
	}
	}).start();
}

else if(e.getActionCommand().equals("stop"))//if stopped then just close the file with whatever written to it and close
{
try
{
csvfile.flush();
csvfile.close();
JOptionPane.showMessageDialog(com.buybazaar.GatherProductDetailsFrame.this,"Processing Interrupted");
}
catch(Exception e1)
{
	e1.printStackTrace();
}
System.exit(0);
}


else if(e.getActionCommand().equals("choose"))//then we have to display a file chooser
{
JFileChooser fileChooser=new JFileChooser();//creating a file chooser
FileNameExtensionFilter filter=new FileNameExtensionFilter("xml files (*.xml)", "xml");//creating.xml filter
fileChooser.setFileFilter(filter);//setting the filter to the file chooser
//if a xml file has been chosen 
if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
temptext.setText(fileChooser.getSelectedFile().getPath());
}

else if(e.getActionCommand().equals("chooseFile"))//then we have to display a file chooser
{
JFileChooser fileChooser=new JFileChooser();//creating a file chooser
FileNameExtensionFilter filter=new FileNameExtensionFilter("txt files (*.txt)", "txt");//creating.txt filter
fileChooser.setFileFilter(filter);//setting the filter to the file chooser
//if a txt file has been chosen 
if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
inputtext.setText(fileChooser.getSelectedFile().getPath());
}


}


//this method collects all the data in the XML template and return then as double dimensional string
private String[][] getSearchKeys(File selectedFile)
{
	String[][] searchKeys=null;
	try
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(selectedFile);
		doc.getDocumentElement().normalize();
   
		NodeList nList = doc.getElementsByTagName("field");
		searchKeys=new String[nList.getLength()][6];
		
		for (int temp = 0; temp < nList.getLength(); temp++) {

		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

		      Element eElement = (Element) nNode;

		      searchKeys[temp][0]=getTagValue("fieldname", eElement);
		      searchKeys[temp][1]=getTagValue("fieldtype", eElement);
		      searchKeys[temp][4]=getTagValue("keyword", eElement);
		   }
		}	
	}	
	catch(Exception e)
	{
	e.printStackTrace();	
	}
	return searchKeys;


}


//gets the value in the specified tag under the given eElement and return the same
private String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 
        Node nValue = (Node) nlList.item(0);
        if(nValue!=null)
	return nValue.getNodeValue();
        else
        	return "";
  }



//this takes the fieldtype(tag name) and the set of keywords and if the keywords are found then this method will return that node
private List<Node> getNodes(String fieldType,String keywords,Document doc)
{
Node node=null;
List<Node> nodelist=new ArrayList<Node>();
NodeList nodes=doc.getElementsByTagName(fieldType);//get all the fields of that type
for(int i=0;i<nodes.getLength();i++)//for each node of that type
{
	node=nodes.item(i);
	if(node!=null &&node.getNextSibling()!=null &&node.getTextContent().trim().equals(keywords))//if the 1st child of the node is text node and also that node must have a next sibling
	{
    	nodelist.add(node);
	}	
}
return nodelist;
}



//takes in a url string and return a cleaned html document object specified by that url
private Document getCleanHtmlDocument(String urlline) throws MalformedURLException, IOException, ParserConfigurationException
{
	//create an html cleaner instance
	HtmlCleaner cleaner = new HtmlCleaner();
	try
	{
    TagNode rootNode = cleaner.clean(new URL(urlline));//get the root node of the document
    // convert to DOM
    CleanerProperties properties = new CleanerProperties();
    properties.setOmitComments(true);//omit the comments in the document
    
    DomSerializer domSerializer = new DomSerializer(properties);//create dom serializer
    Document doc = domSerializer.createDOM(rootNode);//create a DOM from the root node using dom serializer
    doc.getDocumentElement().normalize();
	return doc;
	}
	catch(UnknownHostException e)
	{
		JOptionPane.showMessageDialog(com.buybazaar.GatherProductDetailsFrame.this,"Please check your internet connectivity or look for malfomed URLs in the URL Source file");
		System.exit(0);
	}
	return null;
}
}

