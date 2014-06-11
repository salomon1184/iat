package com.salomon.tools;

import java.io.File;
import java.io.FileWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Json2XML {

	public static void JsonSchema2XML(JSONObject jsonStr, Element root) {

		for (Object key : jsonStr.keySet()) {
			Object value = jsonStr.get(key);
			String name = key.toString();
			String type = "";
			Class valueClass = value.getClass();

			if (valueClass.equals(String.class)) {
				Element stringNode = root.addElement("String");
				stringNode.addAttribute("Name", name);
			} else if (valueClass.equals(Long.class)
					|| valueClass.equals(java.lang.Integer.class)) {
				Element stringNode = root.addElement("Long");
				stringNode.addAttribute("Name", name);
			} else if (valueClass.equals(Boolean.class)) {
				Element stringNode = root.addElement("Boolean");
				stringNode.addAttribute("Name", name);
			} else if (valueClass.equals(JSONObject.class)) {
				Element stringNode = root.addElement("JsonObject");
				stringNode.addAttribute("Name", name);
				JsonSchema2XML(jsonStr.getJSONObject(name), stringNode);
			} else if (valueClass.equals(JSONArray.class)) {
				JSONArray arrayObj = (JSONArray) value;
				if (arrayObj.size() == 0) {
					return;
				}

				if ((arrayObj.get(0).getClass() != JSONObject.class)
						&& (arrayObj.get(0).getClass() != JSONArray.class)) {
					Element stringNode = root.addElement("Array");
					stringNode.addAttribute("Name", name);
				} else {
					Element stringNode = root.addElement("JsonArray");
					stringNode.addAttribute("Name", name);

					for (Object jsonO : arrayObj.toArray()) {
						if (jsonO.getClass() == JSONObject.class) {
							JsonSchema2XML(JSONObject.fromObject(jsonO),
									stringNode);
						}
					}
				}
			}

		}

	}

	public static boolean Doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/* 将document中的内容写入文件中 */
			// 默认为UTF-8格式，指定为"GB2312"
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GB2312");
			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String jsonStr = "{\"address\":[\"北京\"],\"city\":\"昌平\",\"createTime\":1381823621000,\"cxo\":\"\",\"description\":\"牛逼公司\",\"detail\":\"详情1\",\"id\":1,\"industry\":[{\"levelOneIndustry\":{\"id\":\"01\",\"name\":\"IT/互联网/通信/电子\",\"parentId\":\"-1\"},\"levelTwoIndustry\":{\"id\":\"011\",\"name\":\"互联网\",\"parentId\":\"01\"}}],\"level\":13,\"logoUrl\":\"/4/headpic/131104/19/jL6q1An.jpg\",\"managerId\":0,\"middleLogo\":\"http://hdn.mtimg.net//4/headpic/131104/19/jL6q1An_m.jpg\",\"name\":\"新浪微博\",\"orgRegisterTime\":1358154600000,\"origLogo\":\"http://hdn.mtimg.net//4/headpic/131104/19/jL6q1An_o.jpg\",\"province\":\"北京\",\"resourceId\":1,\"telephone\":[\"112233\"],\"type\":0,\"updateTime\":1383565988000,\"website\":[\"http://www.renren.com\"]}";
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		// System.out.println(" type : " + jsonObj.get("arr").getClass());

		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("Result");
		JsonSchema2XML(jsonObj, root);
		System.out.println(document.toString());

		Doc2XmlFile(document, "a.xml");

	}
}
