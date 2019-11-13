/**
 * @author lxm
 * @create_date 2019.9.22
 * @description ͳһб�ܣ�����linux�����±���
 * */
package com.app.tools;
import javax.servlet.http.HttpServletRequest;

public class PathHelper {

    public static String getNormativePath(HttpServletRequest request)
    {
        String path = request.getServletContext().getRealPath("");
        path = path.replace("\\","/");
        return path;
    }

    public static String getNormativePath(HttpServletRequest request,String suffix)
    {
        String path = request.getServletContext().getRealPath(suffix);
        path = path.replace("\\","/");
        return path;
    }
}
