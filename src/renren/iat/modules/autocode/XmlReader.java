package renren.iat.modules.autocode;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlReader {
	/**
	 * 读取xml文档
	 * 
	 */

	/**
	 * 根据xml文档中的内容直接生成InterfaceObj对象
	 * 
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 */
	public InterfaceObj parse(String fileName) throws DocumentException {
		InterfaceObj resultObj = new InterfaceObj();
		Element xmlRoot = this.getXmlRoot(fileName);

		resultObj.setFile(fileName);
		resultObj.setModule(this.getModule(xmlRoot));
		resultObj.setApiName(this.getApiName(xmlRoot));
		resultObj.setDescription(this.getApiDesc(xmlRoot));
		resultObj.setMethod(this.getMethod(xmlRoot));
		resultObj
				.setRequiredParams(this.getParams(xmlRoot, ParamType.REQUIRED));
		resultObj.setOptionalParams(this
				.getParams(xmlRoot, ParamType.OPTIOINAL));

		return resultObj;
	}

	/**
	 * 获取模块及描述
	 * 
	 * @return
	 */
	private String getModule(Element rootElement) {
		String apiName = rootElement.attribute(XmlConfig.NAMEATTR).getText();
		String module = apiName.substring(0, apiName.indexOf(XmlConfig.APISEP));

		return module;
	}

	/**
	 * 获取接口名
	 * 
	 * @return
	 */
	private String getApiName(Element rootElement) {

		if (rootElement.attribute(XmlConfig.NAMEATTR) != null) {
			String apiName = rootElement.attribute(XmlConfig.NAMEATTR)
					.getText();
			return apiName.substring(apiName.indexOf(XmlConfig.APISEP) + 1);
		} else {
			return "";
		}
	}

	/**
	 * 获取接口描述
	 * 
	 * @param rootElement
	 * @return
	 */
	private String getApiDesc(Element rootElement) {
		if (rootElement.attribute(XmlConfig.DESCATTR) != null) {
			return rootElement.attribute(XmlConfig.DESCATTR).getText();
		} else {
			return "";
		}
	}

	/**
	 * 获取请求方式post or get or both
	 * 
	 * @return
	 */
	private MethodType getMethod(Element rootElement) {
		String method = rootElement.attribute(XmlConfig.METHODATTR).getText();
		if (method.equalsIgnoreCase("get")) {
			return MethodType.Get;
		} else if (method.equalsIgnoreCase("post")) {
			return MethodType.Post;
		} else {
			return MethodType.Both;
		}
	}

	/**
	 * 参数类型枚举值
	 * 
	 */
	private enum ParamType {
		REQUIRED, OPTIOINAL
	}

	/**
	 * 获取指定类型的参数列表
	 * 
	 * @param type
	 * @return
	 */
	private HashMap<String, String> getParams(Element rootElement,
			ParamType type) {
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		Element params = null;
		switch (type) {
		case REQUIRED:
			params = rootElement.element(XmlConfig.PARAMATTR).element(
					XmlConfig.REQPARAMATTR);
			break;
		case OPTIOINAL:
			params = rootElement.element(XmlConfig.PARAMATTR).element(
					XmlConfig.OPTPARAMATTR);
			break;
		default:
			break;
		}

		for (@SuppressWarnings("rawtypes")
		Iterator it = params.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			String paramName = element.attribute(XmlConfig.NAMEATTR).getText();
			String paramDesc = element.attribute(XmlConfig.DESCATTR).getText();
			if (paramDesc.equals("")) {
				paramDesc = null;
			}
			paramsMap.put(paramName, paramDesc);
		}
		return paramsMap;
	}

	/**
	 * 获取失败结果 对于有多个错误码的情况以下未考虑，这个待定。。。
	 * 
	 * @return
	 */
	public HashMap<String, String> getFailureResult(Element rootElement) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String errorCode = rootElement.element(XmlConfig.RESULTATTR)
				.element(XmlConfig.FAILRESULTATTR).element(XmlConfig.ERRCODE)
				.attribute(XmlConfig.VALUE).getText();
		String errorMsg = rootElement.element(XmlConfig.RESULTATTR)
				.element(XmlConfig.FAILRESULTATTR).element(XmlConfig.ERRMSG)
				.attribute(XmlConfig.VALUE).getText();

		if (errorMsg.equals("")) {
			errorMsg = null;
		}
		resultMap.put(errorCode, errorMsg);

		return resultMap;
	}

	/**
	 * 返回xml文件的根节点
	 * 
	 * @return
	 * @throws DocumentException
	 */
	private Element getXmlRoot(String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document xmlDoc = null;

		xmlDoc = reader.read(new File(fileName));

		return xmlDoc.getRootElement();
	}
}
