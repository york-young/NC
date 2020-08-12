package com;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SReadChinese {
    public static void main(String[] args) {
        SReadChinese bean=new SReadChinese();
        String fileName="E:\\gitlab\\bim-en\\ecology\\wui\\workflowModel.jsp";
        List<String> result=bean.readChineseFromFile(fileName);
        for (String s:result) {
            System.out.println(s);
        }
    }
    /**
    获取一行文本中所有的字符串
     */
   public List<String> readChinese(String line){
       List<String> result =new ArrayList<String>();
       Pattern p=Pattern.compile("[\\u4e00-\\u9fa5]+");
       Matcher m=p.matcher(line);
       while(m.find()){
           result.add(m.group());
       }
       return result;
   }
   public List<String> readChineseFromFile(String fileName){
       List<String> result =new ArrayList<String>();
       BufferedReader br =null;
       try{
           br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
           String line=null;
           Set<String> set =new HashSet<String>();
           while(null!=(line=br.readLine())){
               List<String> rowChineseList=readChinese(line);
               set.addAll(rowChineseList);
           }
           result.addAll(set);
           //Collections.sort(result);

       }catch (Exception e){
           e.printStackTrace();
       }finally {
           if (null!=br){
               try {
                   br.close();
               }catch (IOException e){
                   e.printStackTrace();
               }finally {
                   br=null;
               }
           }
       }
       return result;
   }
}
