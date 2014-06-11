package renren.iat.modules.autocode;

import java.util.HashMap;

public class InterfaceObj {

	private String file;
	private String module;
	private String apiName;
	private MethodType method;
	private String description;
	private HashMap<String, String> requiredParams;
	private HashMap<String, String> optionalParams;
	private HashMap<String, String> failureResults;

	public String getFile() {
		return this.file;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getApiName() {
		return this.apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public MethodType getMethod() {
		return this.method;
	}

	public void setMethod(MethodType method) {
		this.method = method;
	}

	public HashMap<String, String> getRequiredParams() {
		return this.requiredParams;
	}

	public void setRequiredParams(HashMap<String, String> requiredParams) {
		this.requiredParams = requiredParams;
	}

	public HashMap<String, String> getOptionalParams() {
		return this.optionalParams;
	}

	public void setOptionalParams(HashMap<String, String> optionalParams) {
		this.optionalParams = optionalParams;
	}

	public HashMap<String, String> getFailureResults() {
		return this.failureResults;
	}

	public void setFailureResults(HashMap<String, String> failureResults) {
		this.failureResults = failureResults;
	}

}
