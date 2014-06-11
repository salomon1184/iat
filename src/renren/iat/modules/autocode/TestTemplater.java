package renren.iat.modules.autocode;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TestTemplater implements ITemplater {

	public String parseContent(String packagePre, InterfaceObj apiObj) {
		StringBuilder builder = new StringBuilder();
		builder.append("package ");
		builder.append(packagePre);
		builder.append(".test.");
		builder.append(apiObj.getModule().toLowerCase());
		builder.append(";\n \n");

		builder.append(this.getImportStream(packagePre, apiObj));
		builder.append(this.getClassDefineStartStream(apiObj));
		builder.append(this.getTestMethodDemo(apiObj));
		builder.append(this.getClassDefineEndStream());

		return builder.toString();
	}

	public String getImportStream(String packagePre, InterfaceObj apiObj) {
		StringBuilder builder = new StringBuilder();
		builder.append("import net.sf.json.JSONObject;");
		builder.append("\n");
		builder.append("import org.junit.Test;");
		builder.append("\n");
		builder.append("import ");
		builder.append(packagePre);
		builder.append(".sence.");
		builder.append(apiObj.getModule());
		builder.append(".");
		builder.append(apiObj.getModule().substring(0, 1).toUpperCase());
		builder.append(apiObj.getModule().substring(1).toLowerCase());
		builder.append(apiObj.getApiName().substring(0, 1).toUpperCase());
		builder.append(apiObj.getApiName().substring(1).toLowerCase());
		builder.append("Sence;");
		builder.append("\n \n");

		return builder.toString();
	}

	public String getClassDefineStartStream(InterfaceObj interfaceObj) {
		StringBuilder builder = new StringBuilder();
		builder.append("public class ");
		builder.append(interfaceObj.getModule().substring(0, 1).toUpperCase());
		builder.append(interfaceObj.getModule().substring(1).toLowerCase());
		builder.append(interfaceObj.getApiName().substring(0, 1).toUpperCase());
		builder.append(interfaceObj.getApiName().substring(1).toLowerCase());
		builder.append("Test");
		builder.append(" { \n");

		// 添加空白行。
		builder.append("\n");

		return builder.toString();

	}

	/**
	 * 生成只包含必传参数的sence方法
	 * 
	 * @param interfaceObj
	 * @return
	 */
	public String getTestMethodDemo(InterfaceObj interfaceObj) {

		StringBuilder builder = new StringBuilder();

		// 生成注释
		String upper = interfaceObj.getModule().substring(0, 1).toUpperCase()
				+ interfaceObj.getModule().substring(1).toLowerCase()
				+ interfaceObj.getApiName().substring(0, 1).toUpperCase()
				+ interfaceObj.getApiName().substring(1).toLowerCase();
		builder.append("	@Test(index = 1, summary = \"\", expectedResults = \"\", importance = \"normal\", author = \"\")\n");
		builder.append("	public void ");
		builder.append(upper);

		builder.append("Test_1() throws Exception {\n");
		builder.append("		JSONObject result = ");
		builder.append(upper);
		builder.append("Sence.");
		builder.append(interfaceObj.getApiName().substring(0, 1).toUpperCase());
		builder.append(interfaceObj.getApiName().substring(1).toLowerCase());
		builder.append("(");
		Iterator<Entry<String, String>> iterator = interfaceObj
				.getRequiredParams().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			builder.append("null,");
		}
		builder.append("null");
		builder.append(");\n");

		builder.append("	}\n");

		builder.append("\n");

		return builder.toString();
	}

	public String getClassDefineEndStream() {
		return "\n }";
	}

}
