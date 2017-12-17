package com.thinkgem.jeesite.modules.sys.utils;

/**
 * 功能模块【】
 * 功能说明
 * User: xianqinhong
 * Date: 2017-02-04
 * Time: 14:56
 */
public class StringUtils {

    /**
     * 检查参数是否包含危险字符  双引号 单引号，* ，%， frame script
     * @param param
     * @return   有危险词则返回true
     */
    public static boolean checkInjectWord(String param){
        boolean res = false;
        if(param == null){
            return false;
        }

       String inj = injectChar(param);
        if (!inj.equals(""))
        {
            res = true;
        }

        return res;
    }

    /**
     * 判断字符串中是否含有注入攻击字符
     * @param str
     * @return
     */
    public static String injectChar(String str) {

        //String inj_str = "* % < > & frame script";
    	String inj_str = "* frame script";
        String inj_stra[] = inj_str.split(" ");

        for (int i = 0 ; i < inj_stra.length ; i++ )
        {
            if (str.indexOf(inj_stra[i])>=0)
            {
                return inj_stra[i];
            }
        }
        return "";
    }

    public static void main(String[] argc){
              System.out.println(StringUtils.checkInjectWord("frame"));
              System.out.println(StringUtils.checkInjectWord("script"));
              System.out.println(StringUtils.checkInjectWord("*"));
    }
}
