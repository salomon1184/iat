package com.salomon.tools;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Windows {

	private JFrame frmJsonxml;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Windows window = new Windows();
					window.frmJsonxml.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Windows() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frmJsonxml = new JFrame();
		this.frmJsonxml.setResizable(false);
		this.frmJsonxml.setTitle("Json2XML");
		this.frmJsonxml.setBounds(100, 100, 872, 647);
		this.frmJsonxml.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frmJsonxml.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 327, 586);
		this.frmJsonxml.getContentPane().add(scrollPane);

		final JTextArea textArea_Json = new JTextArea();
		final JTextPane textArea_xml = new JTextPane();
		// textArea_xml.setContentType("text/html");
		scrollPane.setViewportView(textArea_Json);

		JButton button = new JButton("->");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!textArea_Json.getText().isEmpty()) {
					JSONObject jsonObj = JSONObject.fromObject(textArea_Json
							.getText());
					// System.out.println(" type : " +
					// jsonObj.get("arr").getClass());

					Document document = DocumentHelper.createDocument();

					Element root = document.addElement("Result");

					Json2XML.JsonSchema2XML(jsonObj, root);
					System.out.println(document.asXML());

					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("GB2312");
					StringWriter sw = new StringWriter();
					XMLWriter xw = new XMLWriter(sw, format);

					try {
						xw.write(document);
						xw.flush();
						xw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					textArea_xml.setText(sw.toString());
					Json2XML.Doc2XmlFile(document, Utility.getFormatDate()
							+ ".xml");

					StringSelection stringSelection = new StringSelection(sw
							.toString());
					Toolkit.getDefaultToolkit().getSystemClipboard()
							.setContents(stringSelection, null);

				}
			}
		});
		button.setBounds(361, 221, 70, 30);
		this.frmJsonxml.getContentPane().add(button);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(453, 11, 393, 586);
		this.frmJsonxml.getContentPane().add(scrollPane_1);

		scrollPane_1.setViewportView(textArea_xml);
	}
}
