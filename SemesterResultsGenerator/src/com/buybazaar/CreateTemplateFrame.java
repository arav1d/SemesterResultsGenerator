package com.buybazaar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


@SuppressWarnings("serial")
public class CreateTemplateFrame extends JFrame implements ActionListener
{
JTable table;
JTableHeader tableheader;

public CreateTemplateFrame()
{

	
Container c=getContentPane();//getting the content pane of the frame
Box box=Box.createVerticalBox();//creating a vertical box and adding it to the frame
c.add(box);
pack();

//creating the title panel and adding it to the vertical box
JPanel titlepanel=new JPanel();
box.add(titlepanel);

//creating a label for the title and adding it to the title panel
JLabel title=new JLabel("Create Template");
titlepanel.add(title);

//creating the tablepanel and adding it to the vertical box
JPanel tablepanel=new JPanel();
tablepanel.setLayout(new BorderLayout());
box.add(tablepanel);

//creating the column names in a string array
String cols[]={"Field No","Field Name","Field Type","Keyword"};

//creating the rowdata initially with null string values
Object rows[][]={{"","","",""}};


//creating DefaultTableModel with the rows and cols data above
DefaultTableModel model=new DefaultTableModel(rows,cols);

//creating a table with the default table model created
table=new JTable(model);
table.setRowHeight(20);//setting the row height


//creating a scroll pane for the table
JScrollPane scrollpane=new JScrollPane(table);

//creating a  table header for the table
tableheader=table.getTableHeader();

//adding the table header to north and table scrollpane to center
tablepanel.add("North",tableheader);
tablepanel.add("Center",scrollpane);

//create a buttonpanel to add the buttons to the box
JPanel buttonpanel=new JPanel();
box.add(buttonpanel);

//creating add new field button to the buttonpanel
JButton addnewfield=new JButton("Add new field");
buttonpanel.add("South",addnewfield);
addnewfield.setCursor(new Cursor(Cursor.HAND_CURSOR));
addnewfield.setActionCommand("add");//setting the action command to add
addnewfield.addActionListener(this);//adding the action listener to the button

//create delete field
JButton deletefield=new JButton("Delete field");
buttonpanel.add("South",deletefield);
deletefield.setCursor(new Cursor(Cursor.HAND_CURSOR));
deletefield.setActionCommand("delete");//setting the action command to delete
deletefield.addActionListener(this);//adding the action listener to the button


//inserting a new field in the middle
JButton insertfield=new JButton("Insert field");
buttonpanel.add("South",insertfield);
insertfield.setCursor(new Cursor(Cursor.HAND_CURSOR));
insertfield.setActionCommand("insert");//setting the action command to insert
insertfield.addActionListener(this);//adding the action listener to the button

//creating create template button and add it to the buttonpanel
JButton createtemplate=new JButton("Create template");
buttonpanel.add("South",createtemplate);
createtemplate.setCursor(new Cursor(Cursor.HAND_CURSOR));
createtemplate.addActionListener(this);//adding action listener
createtemplate.setActionCommand("create");//setting the action command to create
}

@Override
public void actionPerformed(ActionEvent e) 
{
if(e.getActionCommand().equals("add"))
{
TableModel model=table.getModel();//getting the table model of the data
Object[] rowData={"","","",""};//creating an empty row of data
((DefaultTableModel) model).insertRow(table.getRowCount(),rowData);//inserting that empty row of data into table's model
}

else if(e.getActionCommand().equals("delete"))
{
TableModel model=table.getModel();//getting the table model of the data
int selectedRow=table.getSelectedRow();
if(selectedRow!=-1)
((DefaultTableModel) model).removeRow(selectedRow);//deleting the selected row
}

else if(e.getActionCommand().equals("insert"))
{
TableModel model=table.getModel();//getting the table model of the data
int selectedRow=table.getSelectedRow();//getting the selected row
Object[] rowData={"","","",""};//creating an empty row of data
if(selectedRow!=-1)
((DefaultTableModel) model).insertRow(selectedRow+1,rowData);//inserting a row after the selected row
}

else if(e.getActionCommand().equals("create"))
{
	  if (table.isEditing()) {

          table.getCellEditor().stopCellEditing();

      }
boolean flag=true;
TableModel tModel=table.getModel();
int row=tModel.getRowCount();
for(int i=0;i<row;i++)
{
if(tModel.getValueAt(i, 0).toString().equals("")||tModel.getValueAt(i, 1).toString().equals("")||tModel.getValueAt(i, 2).toString().equals("")||tModel.getValueAt(i, 3).toString().equals(""))
{
	JOptionPane.showMessageDialog(null, "The values of FieldNumber,FieldName,FieldType,Keyword should not be empty at row "+i);
	flag=false;
} 
}
if(flag)
{
JFileChooser fileChooser=new JFileChooser();//creating a file chooser
FileNameExtensionFilter filter=new FileNameExtensionFilter("xml files (*.xml)", "xml");//creating.xml filter
fileChooser.setFileFilter(filter);//setting the filter to the file chooser
if(fileChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
{
	try {
		DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
		Document document=documentBuilder.newDocument();
		
		
		Element root=document.createElement("template");
		document.appendChild(root);
		table.clearSelection();
		String str="";
		for(int i=0;i<row;i++)
		{
			Element elt=document.createElement("field");
			
			str=tModel.getValueAt(i, 0).toString();
				Element child0=document.createElement("fieldnumber");
				child0.appendChild(document.createTextNode(str));
				elt.appendChild(child0);
			str=tModel.getValueAt(i, 1).toString();
				Element child1=document.createElement("fieldname");
				child1.appendChild(document.createTextNode(str));
				elt.appendChild(child1);
			str=tModel.getValueAt(i, 2).toString();
				Element child2=document.createElement("fieldtype");
				child2.appendChild(document.createTextNode(str));
				elt.appendChild(child2);
			str=tModel.getValueAt(i, 3).toString();
				Element child3=document.createElement("keyword");
				child3.appendChild(document.createTextNode(str));
				elt.appendChild(child3);				
			root.appendChild(elt);	
		}
		TransformerFactory transformerFactory=TransformerFactory.newInstance();
		Transformer transformer=transformerFactory.newTransformer();
		
		DOMSource source = new DOMSource(document);
		StreamResult result =  new StreamResult(fileChooser.getSelectedFile());
		transformer.transform(source, result);
	
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
JOptionPane.showMessageDialog(null, "Template created successfully");
this.dispose();
}

}
}
}
