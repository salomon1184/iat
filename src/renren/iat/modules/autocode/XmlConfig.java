package renren.iat.modules.autocode;

public interface XmlConfig {
	public static String NAMEATTR = "Name";   //属性名称
	public static String METHODATTR = "method";   //请求方式描述
	public static String DESCATTR = "desc";    //属性描述
	public static String APIATTR = "Api";      //接口描述
	public static String PARAMATTR = "Params";  //参数描述
	public static String REQPARAMATTR = "Required";  //必传参数描述
	public static String OPTPARAMATTR = "Optional";  //可选参数描述
	public static String APISEP = ".";         //模块分隔符
	public static String RESULTATTR = "Results";   //结果描述
	public static String FAILRESULTATTR = "fail";  //失败结果描述
	public static String VALUE = "value";
	public static String ERRCODE = "errorcode";      //错误代码
	public static String ERRMSG = "errorMessage";   //错误描述
}
