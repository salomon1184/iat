package renren.iat.modules.autocode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class AutoCoder {

	private String packagePre;
	private String codeRootPath;
	private String xmlRootPath;

	public void setCodeRootPath(String codeRootPath) {
		this.codeRootPath = codeRootPath;
	}

	public String getPackagePre() {
		return this.packagePre;
	}

	public void setPackagePre(String packagePre) {
		this.packagePre = packagePre;
	}

	public String getCodeRootPath() {
		return this.codeRootPath;
	}

	public String getXmlRootPath() {
		return this.xmlRootPath;
	}

	public void setXmlRootPath(String xmlRootPath) {
		this.xmlRootPath = xmlRootPath;
	}

	public static void main(String[] args) {
		AutoCoder autoCoder = new AutoCoder();
		List<String> argList = Arrays.asList(args);

		int flag_p = argList.indexOf("-p");
		if ((flag_p == -1) || argList.isEmpty()) {
			helpContent();
			return;
		} else {
			autoCoder.setPackagePre(argList.get(flag_p + 1));
			int flag_c = argList.indexOf("-c");
			int flag_x = argList.indexOf("-x");
			if (flag_c != -1) {
				autoCoder.setCodeRootPath(argList.get(flag_c + 1)
						+ File.separator + "src");
			} else {
				autoCoder.setCodeRootPath("." + File.separator + "src");
			}

			if (flag_x != -1) {
				autoCoder.setXmlRootPath(argList.get(flag_x + 1));
			} else {
				autoCoder.setXmlRootPath("." + File.separator);
			}
		}
		try {
			// 获取xml Root路径下所有xml文件
			File xmlRootPath = new File(autoCoder.getXmlRootPath());
			String[] xmlFiles = xmlRootPath.list(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.toLowerCase().endsWith(".xml");
				}
			});

			// 根据包前缀创建响应子文件夹
			String coderSenceFolderPath = autoCoder.getCodeRootPath()
					+ File.separator + "sence" + File.separator
					+ autoCoder.getPackagePre().replace(".", File.separator)
					+ File.separator + "sence";
			File csf = new File(coderSenceFolderPath);
			csf.mkdirs();

			String coderTestFolderPath = autoCoder.getCodeRootPath()
					+ File.separator + "test" + File.separator
					+ autoCoder.getPackagePre().replace(".", File.separator)
					+ File.separator + "test";
			File ctf = new File(coderSenceFolderPath);
			ctf.mkdirs();

			// 生成代码
			XmlReader reader = new XmlReader();
			SenceTemplater senceemplater = new SenceTemplater();
			TestTemplater testTemplater = new TestTemplater();
			for (String xmlFile : xmlFiles) {

				InterfaceObj result = reader.parse(xmlRootPath + xmlFile);

				String subSenceFolderPath = coderSenceFolderPath
						+ File.separator + result.getModule() + File.separator;
				String classSenceFilePath = subSenceFolderPath
						+ result.getModule().substring(0, 1).toUpperCase()
						+ result.getModule().substring(1).toLowerCase()
						+ result.getApiName().substring(0, 1).toUpperCase()
						+ result.getApiName().substring(1).toLowerCase()
						+ "Sence.java";

				File subSenceFolder = new File(subSenceFolderPath);
				subSenceFolder.mkdirs();

				OutputStreamWriter writer = new OutputStreamWriter(
						new FileOutputStream(classSenceFilePath));
				writer.append(senceemplater.parseContent(
						autoCoder.getPackagePre(), result));
				writer.flush();
				writer.close();

				String subTestFolderPath = coderTestFolderPath + File.separator
						+ result.getModule() + File.separator;
				String classTestFilePath = subTestFolderPath
						+ result.getModule().substring(0, 1).toUpperCase()
						+ result.getModule().substring(1).toLowerCase()
						+ result.getApiName().substring(0, 1).toUpperCase()
						+ result.getApiName().substring(1).toLowerCase()
						+ "Test.java";

				File subTestFolder = new File(subTestFolderPath);
				subTestFolder.mkdirs();

				OutputStreamWriter testWriter = new OutputStreamWriter(
						new FileOutputStream(classTestFilePath));
				testWriter.append(testTemplater.parseContent(
						autoCoder.getPackagePre(), result));
				testWriter.flush();
				testWriter.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			helpContent();
		}

	}

	public static void helpContent() {
		StringBuilder builder = new StringBuilder();
		builder.append("该程序不能正确执行，请输入相关参数(注意参数顺序)： \n");
		builder.append("-p	包前缀，用于指定生成类所属的包前缀(Required). \n");
		builder.append("-x	xml所在根路径，用于指定xml所在的根路径，默认为当前目录(Optional). \n");
		builder.append("-c  code生成根路径， 用于指定生成代码所在的根路径， 默认为当前目录下src文件夹(Optional). \n");
		System.out.println(builder.toString());
	}
}
