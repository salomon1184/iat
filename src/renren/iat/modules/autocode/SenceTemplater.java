package renren.iat.modules.autocode;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SenceTemplater implements ITemplater {

	public String parseContent(String packagePre, InterfaceObj apiObj) {
		StringBuilder builder = new StringBuilder();
		builder.append("package ");
		builder.append(packagePre);
		builder.append(".sence.");
		builder.append(apiObj.getModule().toLowerCase());
		builder.append(";\n \n");

		builder.append(this.getImportStream());
		builder.append(this.getClassDefineStartStream(apiObj));
		builder.append(this.getMethodOnlyRequiredParamsStream(apiObj));
		builder.append(this.getMethodAllParamsStream(apiObj));
		builder.append(this.getClassDefineEndStream());

		return builder.toString();
	}

	public String getImportStream() {
		StringBuilder builder = new StringBuilder();
		builder.append("import renren.iat.modules.user.AUser;");
		builder.append("\n");
		builder.append("import junit.framework.Assert;");
		builder.append("\n");
		builder.append("import net.sf.json.JSONObject;");
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
		builder.append("Sence");
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
	public String getMethodOnlyRequiredParamsStream(InterfaceObj interfaceObj) {

		StringBuilder builder = new StringBuilder();

		// 生成注释
		builder.append("	/**\n");
		builder.append("	 *\n");

		StringBuilder paramBuilder = new StringBuilder();
		StringBuilder putParamBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = interfaceObj
				.getRequiredParams().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			builder.append("	 * @param ");
			builder.append(entry.getKey());

			builder.append("\n");
			if ((entry.getValue() != null) && entry.getValue().isEmpty()) {
				builder.append("	 *            ");
				builder.append(entry.getValue());
				builder.append("\n");
			}

			String param = entry.getKey().toLowerCase();

			// 拼接方法参数字符串
			paramBuilder.append(", String ");
			paramBuilder.append(param);

			// 拼接putParam方法主体字符串
			putParamBuilder.append("		user.PutParam(\"");
			putParamBuilder.append(param);
			putParamBuilder.append("\", ");
			putParamBuilder.append(param);
			putParamBuilder.append(");\n");
		}

		builder.append("	 */\n");
		builder.append("	public static JSONObject ");
		builder.append(interfaceObj.getApiName().substring(0, 1).toUpperCase());
		builder.append(interfaceObj.getApiName().substring(1).toLowerCase());
		builder.append("(AUser user");
		builder.append(paramBuilder.toString());
		builder.append(") { \n");

		builder.append(putParamBuilder.toString());
		builder.append("\n");
		builder.append("		return user.Invoke3GApiCommand(\"");
		builder.append(interfaceObj.getModule());
		builder.append(".");
		builder.append(interfaceObj.getApiName());

		if (interfaceObj.getMethod() == MethodType.Get) {
			builder.append(", false");
		}

		builder.append("\");\n");

		builder.append("	}\n");

		builder.append("\n");

		return builder.toString();
	}

	/**
	 * 生成只包含必传参数的sence方法
	 * 
	 * @param interfaceObj
	 * @return
	 */
	public String getMethodAllParamsStream(InterfaceObj interfaceObj) {

		// 如果没有可选参数，则不生成任何方法
		if (interfaceObj.getOptionalParams().size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		// 生成注释
		builder.append("	/**\n");
		builder.append("	 *\n");

		StringBuilder paramBuilder = new StringBuilder();
		StringBuilder putParamBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = interfaceObj
				.getRequiredParams().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			builder.append("	 * @param ");
			builder.append(entry.getKey());

			builder.append("\n");
			if ((entry.getValue() != null) && entry.getValue().isEmpty()) {
				builder.append("	 *            ");
				builder.append(entry.getValue());
				builder.append("\n");
			}

			String param = entry.getKey().toLowerCase();

			// 拼接方法参数字符串
			paramBuilder.append(", String ");
			paramBuilder.append(param);

			// 拼接putParam方法主体字符串
			putParamBuilder.append("		user.PutParam(\"");
			putParamBuilder.append(param);
			putParamBuilder.append("\", ");
			putParamBuilder.append(param);
			putParamBuilder.append(");\n");
		}

		Iterator<Entry<String, String>> iterator2 = interfaceObj
				.getOptionalParams().entrySet().iterator();
		while (iterator2.hasNext()) {
			Map.Entry<String, String> entry = iterator2.next();
			builder.append("	 * @param ");
			builder.append(entry.getKey());

			builder.append("\n");
			if ((entry.getValue() != null) && entry.getValue().isEmpty()) {
				builder.append("	 *            ");
				builder.append(entry.getValue());
				builder.append("\n");
			}

			String param = entry.getKey().toLowerCase();

			// 拼接方法参数字符串
			paramBuilder.append(", String ");
			paramBuilder.append(param);

			// 拼接putParam方法主体字符串
			putParamBuilder.append("		user.PutParam(\"");
			putParamBuilder.append(param);
			putParamBuilder.append("\", ");
			putParamBuilder.append(param);
			putParamBuilder.append(");\n");
		}

		builder.append("	 */\n");
		builder.append("	public static JSONObject ");
		builder.append(interfaceObj.getApiName().substring(0, 1).toUpperCase());
		builder.append(interfaceObj.getApiName().substring(1).toLowerCase());
		builder.append("(AUser user");
		builder.append(paramBuilder.toString());
		builder.append(") { \n");

		builder.append(putParamBuilder.toString());
		builder.append("\n");
		builder.append("		return user.Invoke3GApiCommand(\"");
		builder.append(interfaceObj.getModule());
		builder.append(".");
		builder.append(interfaceObj.getApiName());

		if (interfaceObj.getMethod() == MethodType.Get) {
			builder.append(", false");
		}

		builder.append("\");\n");

		builder.append("	}\n");

		builder.append("\n");

		return builder.toString();
	}

	public String getClassDefineEndStream() {
		return "\n }";
	}
}
