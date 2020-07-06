package com;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * 获取一些特定的日期和数据的工具类
 * @author York
 */

public class QueryDataUtil {
    public static void main(String[] args) {
        System.out.println(queryDateUtil());
        //System.out.println(getRetCode(postResult));
        //System.out.println(getRetMsg(postResult));
        //System.out.println(getRandom(18));
    }

    public static String FORMAT_FULL_XML = "yyyyMMddHHmmssSSS";
    public static String NCIp = "192.168.0.115";
    public static String NCPort = "7070";
    public static String NCPort2 = "7080";
    public static String postResult = null;
    public static String sCoding = "GBK";
    public static String cmpVersion = "0.0.1.0";
    public static String sZip = "0";
    public static String sLanguage = "zh_CN";
    //public static String sPackageID = queryDateUtil()+getRandom(18);
    //支付指令提交交易码
    public static String sTransCodePAYENT = "PAYENT";
    //高级新多账户余额查询交易码
    public static String sTransCodeQACCBAL = "QACCBAL";
    //支付指令查询交易码
    public static String sTransCodeQPAYENT = "QPAYENT";
    //企业财务室提交交易码
    public static String sTransCodePAYPER = "PAYPER";
    //企业财务室查询交易码
    public static String sTransCodeQPAYPER = "QPAYPER";
    public static String sBankCode = "102";
    public static String sGroupCIS = "110290001987990";
    public static String sID = "FangYQHL191224.y.1102";

    /**
     * 获取当前时间
     */

    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL_XML);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取指定位数随机数的方法
     *
     * @param n
     * @return
     */
    public static String getRandom(int n) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 1; i <= n; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 获取发送日期并转换格式
     *
     * @return
     */
    public static String getSendDate() {
        String timeStr = getTimeString();
        String sendDate = timeStr.substring(0, 8);
        return sendDate;
    }

    /**
     * 获取发送时间并转换字符串格式
     *
     * @return
     */
    public static String getSendTime() {
        String timeStr = getTimeString();
        String sendTime = timeStr.substring(8);
        return sendTime;
    }

    /**
     * @Description:
     *             获取银行的工作日期，为了获取签名的时间
     * @Author: York
     * @Date: 2020/7/3 13:26
     * @param
     * @Return: java.lang.String
     **/
    public static String queryDateUtil() {
        StringBuilder sContent = new StringBuilder();
        sContent.append("<?xml version=\"1.0\" encoding = \"GBK\"?><CMS><eb><pub>").append("<TransCode>").append(sTransCodeQACCBAL).append("</TransCode>")
                .append(" <CIS>").append(sGroupCIS).append("</CIS>").append("<BankCode>").append(sBankCode).append("</BankCode>").append("<ID>").append(sID).append("</ID> ")
                .append("<TranDate>").append(getSendDate()).append("</TranDate> ").append("<TranTime>").append(getSendTime()).append("</TranTime>")
                .append("<fSeqno>").append("1111111").append("</fSeqno></pub><in>").append("<TotalNum>1</TotalNum><BLFlag></BLFlag><SynFlag></SynFlag>")
                .append("<rd><iSeqno>1111</iSeqno><AccNo>1102021929000036344</AccNo><CurrType>001</CurrType><ReqReserved3></ReqReserved3><AcctSeq></AcctSeq><MainAcctNo></MainAcctNo></rd> ")
                .append("</in></eb></CMS>");
        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://").append(NCIp).append(":").append(NCPort).append("/servlet/ICBCCMPAPIReqServlet?PackageID=")
                .append("1111111").append("&SendTime=").append(getTimeString());
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(urlStr.toString());
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
            post.addParameter("Version", cmpVersion);
            post.addParameter("TransCode", sTransCodeQACCBAL);
            post.addParameter("BankCode", sBankCode);
            post.addParameter("GroupCIS", sGroupCIS);
            post.addParameter("ID", sID);
            post.addParameter("PackageID", "1111111");
            post.addParameter("Cert", "");
            post.addParameter("Language", sLanguage);
            post.addParameter("reqData", sContent.toString());
            //获得http返回码
            int returnFlag = client.executeMethod(post);
            try {
                postResult = post.getResponseBodyAsString();
                if (postResult.startsWith("reqData=")) {
                    postResult = postResult.substring(8);
                    byte[] decodeResult = getFromBASE64(postResult);
                    postResult = new String(decodeResult, sCoding);
                }
                String dateStr = postResult.split("<LastTranDate>")[1];
                String date = dateStr.substring(0, 10).replace("-", "");
                //System.out.println(date);
                return date;
            } catch (Exception e) {
                e.printStackTrace();
            }
            post.releaseConnection(); //释放http连接
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取头部交易指令返回码
     *
     * @param msg
     * @return
     */
    public static String getRetCode(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<RetCode>")) {
            return null;
        }
        String retCode = msg.split("<RetCode>")[1].split("</RetCode>")[0];
        return retCode;
    }

    /**
     * 获取交易返回信息
     *
     * @param msg
     * @return
     */
    public static String getIRetMsg(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<iRetMsg>")) {
            return null;
        }
        String iRetMsg = msg.split("<iRetMsg>")[1].split("</iRetMsg>")[0];
        return iRetMsg;
    }

    /**
     * 获取交易指令返回码
     *
     * @param msg
     * @return
     */
    public static String getIRetCode(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<iRetCode>")) {
            return null;
        }
        String iRetCode = msg.split("<iRetCode>")[1].split("</iRetCode>")[0];
        return iRetCode;
    }

    /**
     * 获取待查指令包序列号
     *
     * @param msg
     * @return
     */
    public static String getQryfSeqno(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<QryfSeqno>")) {
            return null;
        }
        String qryfSeqno = msg.split("<QryfSeqno>")[1].split("</QryfSeqno>")[0];
        return qryfSeqno;
    }

    /**
     * 获取待查平台交易序列号
     *
     * @param msg
     * @return
     */
    public static String getQrySerialNo(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<QrySerialNo>")) {
            return null;
        }
        String qrySerialNo = msg.split("<QrySerialNo>")[1].split("</QrySerialNo>")[0];
        return qrySerialNo;
    }

    /**
     * 获取待查指令包序列号
     *
     * @param msg
     * @return
     */
    public static String getfSeqno(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<fSeqno>")) {
            return null;
        }
        String fSeqno = msg.split("<fSeqno>")[1].split("</fSeqno>")[0];
        return fSeqno;
    }

    /**
     * 获取待查平台交易序列号
     *
     * @param msg
     * @return
     */
    public static String getSerialNo(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<SerialNo>")) {
            return null;
        }
        String serialNo = msg.split("<SerialNo>")[1].split("</SerialNo>")[0];
        return serialNo;
    }

    /**
     * 获取交易指令返回码
     *
     * @param msg
     * @return
     */
    public static String getResult(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<Result>")) {
            return null;
        }
        String result = msg.split("<Result>")[1].split("</Result>")[0];
        return result;
    }

    /**
     * 获取交易指令返回码
     *
     * @param msg
     * @return
     */
    public static String getTotalAmt(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<TotalAmt>")) {
            return null;
        }
        String totalAmt = msg.split("<TotalAmt>")[1].split("</TotalAmt>")[0];
        return totalAmt;
    }

    /**
     * 获取交易头部返回信息
     *
     * @param msg
     * @return
     */
    public static String getRetMsg(String msg) {
        if (msg == null || "".equals(msg) || !msg.contains("<RetMsg>")) {
            return null;
        }
        String retMsg = msg.split("<RetMsg>")[1].split("</RetMsg>")[0];
        return retMsg;
    }

    /**
     * 将加密的字符串解密
     *
     * @param s
     * @return
     */
    public static byte[] getFromBASE64(String s) {
        if (s == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(s);
        } catch (Exception e) {
            return null;
        }
    }
}
