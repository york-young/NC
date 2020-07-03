package com;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.QueryDataUtil.*;
import static com.QueryDataUtil.getTimeString;

/**
 * @author York
 */

public class BanksLinkEnterprises {

    String sPackageID = queryDateUtil() + getRandom(18);

    /**
     * 单笔指令操作提交测试
     */
    @Test
    public void test1() {
        String sysout = payInstructionSubmission();
        System.out.println(sysout);
        if ("0".equals(getRetCode(sysout))) {
            System.out.println("指令上送成功了");
            //payInstructionQuery(getQryfSeqno(sysout), getQrySerialNo(sysout));
            int result = Integer.valueOf(getResult(sysout));
            if (result == 7) {
                System.out.println("实付金额：" + getTotalAmt(sysout));
            } else if (result == 6 || result == 8) {
                System.out.println("实付金额：0");
                System.out.println(getIRetCode(sysout) + getIRetMsg(sysout));
            } else {
                payInstructionQuery(getQryfSeqno(sysout), getQrySerialNo(sysout));
            }
        } else {
            System.out.println("指令上送失败了:" + getRetCode(sysout) + getRetMsg(sysout));
        }
    }

    /**
     * 多笔指令操作提交测试
     */
    @Test
    public void test2() {
        String sysout = enterpriseFinanceDirectiveSubmission();
        System.out.println("sysout" + sysout);
        if ("0".equals(getRetCode(sysout))) {
            System.out.println("指令上送成功了");
            String serialNo = getSerialNo(sysout);
            String fSeqno = getfSeqno(sysout);
            String sys = enterprisesFinancialInstructionQueery(fSeqno, serialNo);
            System.out.println(sys);

            /*int result = Integer.valueOf(getResult(sys));
            if (result == 7) {
                System.out.println("实付金额：" + getTotalAmt(sys));

            } else if (result == 6 || result == 8) {
                System.out.println("实付金额：0");
                System.out.println(getIRetCode(sys) + getIRetMsg(sys));
            } else {
                payInstructionQuery(getQryfSeqno(sys), getQrySerialNo(sys));
            }*/
        } else {
            System.out.println("指令上送失败了:" + getRetCode(sysout) + getRetMsg(sysout));
        }


    }

    /**
     * 支付指令提交方法
     */
    public String payInstructionSubmission() {
        try {
            String payAccNo = "1102021929000036344";
            String payAccNameCN = "强貌坡迄匆末魏输移氯零闸兔迄";
            String recAccNo = "1102022009000767169";
            String recAccNameCN = "强貌坡迄匆末魏输移氯零闸涛建桂迄";

            //入账方式 2：并笔记账 0：逐笔记账,支付指令提交用0，企业用2
            String settleMode = "0";
            String totalNum = "1";
            String totalAmt = "1";
            String payAmt = "1";

            String payType = "1";
            String sysIOFlg = "1";
            String sPackageID = QueryDataUtil.getTimeString() + getRandom(18);

            StringBuilder sContentSign = new StringBuilder();
            sContentSign.append("<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub>")
                    .append("<TransCode>").append(sTransCodePAYENT).append("</TransCode>")
                    .append("<CIS>").append(sGroupCIS).append("</CIS>")
                    .append("<BankCode>").append(sBankCode).append("</BankCode>")
                    .append("<ID>").append(sID).append("</ID>")
                    .append("<TranDate>").append(queryDateUtil()).append("</TranDate>")
                    .append("<TranTime>").append(getSendTime()).append("</TranTime>")
                    .append("<fSeqno>").append(sPackageID).append("</fSeqno></pub><in>")
                    .append("<OnlBatF></OnlBatF><SettleMode>").append(settleMode).append("</SettleMode>")
                    .append("<TotalNum>").append(totalNum).append("</TotalNum><TotalAmt>").append(totalAmt).append("</TotalAmt>")
                    .append("<SignTime>").append(queryDateUtil()).append(getSendTime()).append("</SignTime>")
                    .append("<ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><AlertFlag>1</AlertFlag>");

            sContentSign.append("<rd><iSeqno>").append(getRandom(20)).append("</iSeqno><ReimburseNo></ReimburseNo><ReimburseNum></ReimburseNum>")
                    .append("<StartDate></StartDate><StartTime></StartTime>")
                    .append(" <PayType>").append(payType).append("</PayType><PayAccNo>").append(payAccNo).append("</PayAccNo>")
                    .append("<PayAccNameCN>").append(payAccNameCN).append("</PayAccNameCN><PayAccNameEN></PayAccNameEN>")
                    .append("<RecAccNo>").append(recAccNo).append("</RecAccNo>")
                    .append("<RecAccNameCN>").append(recAccNameCN).append("</RecAccNameCN> <RecAccNameEN></RecAccNameEN> ")
                    .append("<SysIOFlg>").append(sysIOFlg).append("</SysIOFlg>").append("<IsSameCity></IsSameCity><Prop></Prop>")
                    .append("<RecICBCCode></RecICBCCode><RecCityName></RecCityName><RecBankNo></RecBankNo><RecBankName></RecBankName>")
                    .append("<CurrType>001</CurrType><PayAmt>").append(payAmt).append("</PayAmt><UseCode></UseCode><UseCN></UseCN><EnSummary></EnSummary>")
                    .append("<PostScript></PostScript><Summary></Summary><Ref></Ref><Oref></Oref><ERPSqn></ERPSqn><BusCode></BusCode>")
                    .append("<ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName><CrvouhNo></CrvouhNo><BankType></BankType>")
                    .append("<FileNames></FileNames><Indexs></Indexs><PaySubNo></PaySubNo><RecSubNo></RecSubNo><MCardNo></MCardNo><MCardName></MCardName></rd>");

            sContentSign.append("</in></eb></CMS>");
            //NCPort2 签名端口号
            java.net.URL aURL = new java.net.URL("http://" + NCIp + ":" + NCPort2);
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);


            urlConnection.setRequestProperty("Content-Length", String.valueOf(sContentSign.toString().getBytes(sCoding).length));
            urlConnection.setRequestProperty("Content-Type", "INFOSEC_SIGN/1.0");
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(sContentSign.toString().getBytes(sCoding));
            out.flush();
            out.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("NC signature failed");
            }
            //NC签名结果
            String resM = urlConnection.getResponseMessage();

            StringBuffer repContent = new StringBuffer("");
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);

            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                repContent.append(readLine);
            }
            in.close();
            urlConnection.disconnect();
            String repSignContent = null;

            try {
                repSignContent = repContent.toString().split("<sign>")[1].split("</sign>")[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
            String urlStr1 = "http://" + NCIp + ":" + NCPort + "/servlet/ICBCCMPAPIReqServlet?PackageID=" + sPackageID + "&SendTime=" + getTimeString();
            //构建http客户端
            HttpClient myclient = new HttpClient();
            //加密端口
            PostMethod mypost = new PostMethod(urlStr1);

            mypost.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=GBK");
            mypost.addParameter("Version", cmpVersion);
            mypost.addParameter("TransCode", sTransCodePAYENT);
            mypost.addParameter("BankCode", sBankCode);
            mypost.addParameter("GroupCIS", sGroupCIS);
            mypost.addParameter("ID", sID);
            mypost.addParameter("PackageID", sPackageID);
            mypost.addParameter("Cert", "");
            mypost.addParameter("Language", sLanguage);
            mypost.addParameter("zipFlag", sZip);
            mypost.addParameter("reqData", repSignContent);

            int returnFlag = myclient.executeMethod(mypost);

            try {
                String postResult = mypost.getResponseBodyAsString();
                if (postResult.startsWith("reqData=")) {
                    postResult = postResult.substring(8);
                }
                byte[] decodeResult = getFromBASE64(postResult);
                String sysout = new String(decodeResult, sCoding);
                return sysout;

            } catch (Exception e) {
                e.printStackTrace();
            }
            mypost.releaseConnection(); //释放http连接


        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
            System.err.println("error:" + e.getMessage());
        }
        return null;
    }

    /**
     * 企业财务室指令提交方法
     *
     * @return
     */
    public String enterpriseFinanceDirectiveSubmission() {
        String totalNum = "2";
        String totalAmt = "2";
        String payAccNo = "1102021929000036344";
        String payAccNameCN = "强貌坡迄匆末魏输移氯零闸兔迄";
        String recAccNo = "1102022009000767169";
        String recAccNameCN = "强貌坡迄匆末魏输移氯零闸涛建桂迄";
        String recAccNo1 = "1102027009913534215";
        String recAccNameCN1 = "强貌坡迄匆末魏输移氯樱圾婆桂迄";
        String currType = "001";
        String payAmt = "1";
        String useCN = "用途中文描述";

        try {
            StringBuilder sContentSign = new StringBuilder();

            sContentSign.append("<?xml version=\"1.0\" encoding = \"GBK\"?><CMS><eb><pub>").append("<TransCode>").append(sTransCodePAYPER).append("</TransCode>")
                    .append("<CIS>").append(sGroupCIS).append("</CIS>").append("<BankCode>").append(sBankCode).append("</BankCode>").append("<ID>").append(sID).append("</ID>")
                    .append("<TranDate>").append(getSendDate()).append("</TranDate>").append("<TranTime>").append(getSendTime()).append("</TranTime>")
                    .append("<fSeqno>").append(sPackageID).append("</fSeqno></pub><in><OnlBatF>1</OnlBatF><SettleMode>2</SettleMode>")
                    .append("<TotalNum>").append(totalNum).append("</TotalNum>").append("<TotalAmt>").append(totalAmt).append("</TotalAmt>")
                    .append("<SignTime>").append(queryDateUtil()).append(getSendTime()).append("</SignTime><THBaseAccFlag>0</THBaseAccFlag>")
                    .append("<RegSerialNO></RegSerialNO><PackageName></PackageName><TotalSummary></TotalSummary><BatchSumFlag></BatchSumFlag>");

            sContentSign.append("<rd><iSeqno>").append(getRandom(10)).append("</iSeqno><ReimburseNo></ReimburseNo><ReimburseNum></ReimburseNum>")
                    .append("<StartDate></StartDate><StartTime></StartTime><PayType>1</PayType>")
                    .append("<PayAccNo>").append(payAccNo).append("</PayAccNo>").append("<PayAccNameCN>").append(payAccNameCN).append("</PayAccNameCN><PayAccNameEN></PayAccNameEN>")
                    .append("<RecAccNo>").append(recAccNo).append("</RecAccNo>").append("<RecAccNameCN>").append(recAccNameCN).append("</RecAccNameCN><RecAccNameEN></RecAccNameEN>")
                    .append("<SysIOFlg></SysIOFlg><IsSameCity></IsSameCity><RecICBCCode></RecICBCCode><RecCityName></RecCityName><RecBankNo></RecBankNo><RecBankName></RecBankName>")
                    .append("<CurrType>").append(currType).append("</CurrType>").append("<PayAmt>").append(payAmt).append("</PayAmt><UseCode></UseCode>")
                    .append("<UseCN>").append(useCN).append("</UseCN>").append("<EnSummary></EnSummary><PostScript></PostScript><Summary></Summary><Ref></Ref>")
                    .append("<Oref>1</Oref><ERPSqn></ERPSqn><BusCode></BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName>")
                    .append("<CrvouhNo></CrvouhNo><MCardNo></MCardNo><MCardName></MCardName></rd>");

            sContentSign.append("<rd><iSeqno>").append(getRandom(10)).append("</iSeqno><ReimburseNo></ReimburseNo><ReimburseNum></ReimburseNum>")
                    .append("<StartDate></StartDate><StartTime></StartTime><PayType>1</PayType>")
                    .append("<PayAccNo>").append(payAccNo).append("</PayAccNo>").append("<PayAccNameCN>").append(payAccNameCN).append("</PayAccNameCN><PayAccNameEN></PayAccNameEN>")
                    .append("<RecAccNo>").append(recAccNo1).append("</RecAccNo>").append("<RecAccNameCN>").append(recAccNameCN1).append("</RecAccNameCN><RecAccNameEN></RecAccNameEN>")
                    .append("<SysIOFlg></SysIOFlg><IsSameCity></IsSameCity><RecICBCCode></RecICBCCode><RecCityName></RecCityName><RecBankNo></RecBankNo><RecBankName></RecBankName>")
                    .append("<CurrType>").append(currType).append("</CurrType>").append("<PayAmt>").append(payAmt).append("</PayAmt><UseCode></UseCode>")
                    .append("<UseCN>").append(useCN).append("</UseCN>").append("<EnSummary></EnSummary><PostScript></PostScript><Summary></Summary><Ref></Ref>")
                    .append("<Oref>1</Oref><ERPSqn></ERPSqn><BusCode></BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName>")
                    .append("<CrvouhNo></CrvouhNo><MCardNo></MCardNo><MCardName></MCardName></rd>");

            sContentSign.append("</in></eb></CMS>");
            //NCPort2 签名端口号
            java.net.URL aURL = new java.net.URL("http://" + NCIp + ":" + NCPort2);
            //打开和URL之间的连接
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            //设置通用的请求属性
            urlConnection.setRequestProperty("Content-Length", String.valueOf(sContentSign.toString().getBytes(sCoding).length));
            urlConnection.setRequestProperty("Content-Type", "INFOSEC_SIGN/1.0");
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(sContentSign.toString().getBytes(sCoding));
            out.flush();
            out.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("NC  failed");
            }
            //NC签名结果
            String resM = urlConnection.getResponseMessage();

            StringBuffer repContent = new StringBuffer();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);

            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                repContent.append(readLine);
            }
            in.close();
            urlConnection.disconnect();

            int beginSign = 0;
            int endSign = 0;
            try {

                beginSign = repContent.indexOf("<sign>") + 6;
                endSign = repContent.indexOf("</sign>");
            } catch (Exception e) {
                System.out.println("！！！！！！！！！！please check NC set！！！！！！！！！！");
            }

            String repSignContent = repContent.substring(beginSign, endSign);
            System.out.println("repSignContent==" + repSignContent);
            StringBuilder urlStr = new StringBuilder();
            urlStr.append("http://").append(NCIp).append(":").append(NCPort).append("/servlet/ICBCCMPAPIReqServlet?PackageID=").append(sPackageID)
                    .append("&SendTime=").append(getTimeString());
            //String urlStr1 = "http://" + NCIp + ":" + NCPort + "/servlet/ICBCCMPAPIReqServlet?PackageID=" + sPackageID + "&SendTime=" +getTimeString();
            //构建http客户端
            HttpClient myClient = new HttpClient();
            //加密端口
            PostMethod myPost = new PostMethod(urlStr.toString());

            myPost.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=GBK");

            myPost.addParameter("Version", cmpVersion);
            myPost.addParameter("TransCode", sTransCodePAYPER);
            myPost.addParameter("BankCode", sBankCode);
            myPost.addParameter("GroupCIS", sGroupCIS);
            myPost.addParameter("ID", sID);
            myPost.addParameter("PackageID", sPackageID);
//            myPost.addParameter("Cert", "");
            myPost.addParameter("Language", sLanguage);
            myPost.addParameter("zipFlag", sZip);

            myPost.addParameter("reqData", repSignContent);

            System.out.println("start send jiami..." + System.currentTimeMillis());
            //获得http返回码
            int returnFlag = myClient.executeMethod(myPost);

            try {
                String postResult = myPost.getResponseBodyAsString();
                if (postResult.startsWith("reqData=")) {
                    postResult = postResult.substring(8);
                }
                System.out.println("******************************NC back******************************");
                System.out.println(new String(postResult));

                byte[] decodeResult = getFromBASE64(postResult);
                String sysout = new String(decodeResult, sCoding);

                System.out.println("******************************back data******************************");
                System.out.println(sysout);
                return sysout;

            } catch (Exception e) {
                e.printStackTrace();
            }
            // 释放http连接
            myPost.releaseConnection();


        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
            System.err.println("error:" + e.getMessage());
        }
        return null;
    }

    /**
     * 支付指令查询方法
     *
     * @param qryfSeqno
     * @param qrySerialNo
     */
    public String payInstructionQuery(String qryfSeqno, String qrySerialNo) {
        StringBuilder sContent = new StringBuilder();
        sContent.append("<?xml version=\"1.0\" encoding = \"GBK\"?><CMS><eb><pub>");
        sContent.append("<TransCode>").append(sTransCodeQPAYENT).append("</TransCode>");
        sContent.append("<CIS>").append(sGroupCIS).append("</CIS>");
        sContent.append("<BankCode>").append(sBankCode).append("</BankCode>");
        sContent.append("<ID>").append(sID).append("</ID>");
        sContent.append("<TranDate>").append(queryDateUtil()).append("</TranDate> ");
        sContent.append("<TranTime>").append(getSendTime()).append("</TranTime> ");
        sContent.append("<fSeqno>").append(getTimeString()).append("</fSeqno>").append("</pub><in> ");
        sContent.append("<QryfSeqno>").append(qryfSeqno).append("</QryfSeqno>");
        sContent.append("<QrySerialNo>").append(qrySerialNo).append("</QrySerialNo>").append("</in></eb></CMS>");

        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://").append(NCIp).append(":").append(NCPort).append("/servlet/ICBCCMPAPIReqServlet?PackageID=").append(sPackageID)
                .append("&SendTime=").append(getTimeString());
        try {


            /**
             * 构建http客户端
             */
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(urlStr.toString());
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
            post.addParameter("Version", cmpVersion);
            post.addParameter("TransCode", sTransCodeQPAYENT);
            post.addParameter("BankCode", sBankCode);
            post.addParameter("GroupCIS", sGroupCIS);
            post.addParameter("ID", sID);
            post.addParameter("PackageID", sPackageID);
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
                return postResult;
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
     * 企业财务指令查询方法
     * @return
     */
    public String enterprisesFinancialInstructionQueery(String qryfSeqno, String qrySerialNo) {
        StringBuilder sContent = new StringBuilder();
        sContent.append("<?xml version=\"1.0\" encoding = \"GBK\"?><CMS><eb><pub>")
                .append("<TransCode>").append(sTransCodeQPAYPER).append("</TransCode>")
                .append("<CIS>").append(sGroupCIS).append("</CIS>")
                .append("<BankCode>").append(sBankCode).append("</BankCode>")
                .append("<ID>").append(sID).append("</ID>")
                .append("<TranDate>").append(getSendDate()).append("</TranDate> ")
                .append("<TranTime>").append(getSendTime()).append("</TranTime> ")
                .append("<fSeqno>").append(sPackageID).append("</fSeqno>").append("</pub><in> ");
        if (null == qryfSeqno) {
            sContent.append("<QryfSeqno>").append("</QryfSeqno>");
        } else {
            sContent.append("<QryfSeqno>").append(qryfSeqno).append("</QryfSeqno>");
        }
        if (null == qrySerialNo) {
            sContent.append("<QrySerialNo>").append("</QrySerialNo>");
        } else {
            sContent.append("<QrySerialNo>").append(qrySerialNo).append("</QrySerialNo>");
        }
        sContent.append("</in></eb></CMS>");

        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://").append(NCIp).append(":").append(NCPort).append("/servlet/ICBCCMPAPIReqServlet?PackageID=").append(sPackageID)
                .append("&SendTime=").append(getTimeString());
        try {
            /**
             * 构建http客户端
             */
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(urlStr.toString());
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
            post.addParameter("Version", cmpVersion);
            post.addParameter("TransCode", sTransCodeQPAYPER);
            post.addParameter("BankCode", sBankCode);
            post.addParameter("GroupCIS", sGroupCIS);
            post.addParameter("ID", sID);
            post.addParameter("PackageID", sPackageID);
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
                return postResult;
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

    public String Signature(){
        return null;
    }


}
