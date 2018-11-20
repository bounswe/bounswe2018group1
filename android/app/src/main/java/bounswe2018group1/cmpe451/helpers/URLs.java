package bounswe2018group1.cmpe451.helpers;

public class URLs {

    public static final String URL_ACTIVATE = "http://52.7.87.173:5000/activate";
    public static final String URL_LOGIN = "http://52.7.87.173:5000/login";
    public static final String URL_LOGOUT = "http://52.7.87.173:5000/logout";
    public static final String URL_REGISTER = "http://52.7.87.173:5000/register";

    public static final String URL_MEMORY = "http://52.7.87.173:5000/memory";
    public static final String URL_MEMORY_ALL = "http://52.7.87.173:5000/memory/all";
    public static final String URL_MEMORY_USER = "http://52.7.87.173:5000/memory/user";

    public static final String URL_USER = "http://52.7.87.173:5000/user";
    public static final String URL_USER_ALL = "http://52.7.87.173:5000/user/all";
    public static final String URL_USER_INFO = "http://52.7.87.173:5000/user/info";

    public static String URL_USER_withID(String id) {
        return URL_USER + '/' + id;
    }

}
