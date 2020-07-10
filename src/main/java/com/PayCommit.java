package com;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PayCommit {

    public static void main(String[] args) throws Exception {

        // NC IP地址
        String NCIp = "192.168.0.115";
        // NC 加密端口号
        String NCPort = "7070";
        // 签名端口号
        String NCPort2 = "7080";
        //是否对交易签名
        boolean signatureflg = true;
        //String retcertPath = "C:\\Users\\11150\\Documents\\NC3\\FangYQHL191224.pfx";//工行公钥 admin.crt所在路径

        try {
            //编码格式
            String sCoding = "GBK";
            //接口版本 0.0.0.1或者0.0.1.0
            String cmpVersion = "0.0.1.0";
            //是否大文件方式
            String sZip = "0";
            String sLanguage = "zh_CN";
            //下面字段与明文xml包中保持一致
            //包序列号与xml包中保持一致
            String sPackageID = "11111459111111111";
            //交易代码
            String sTransCode = "PAYENT";
            //银行编码
            String sBankCode = "102";
            //集团CIS号
            String sGroupCIS = "110290001987990";
            //证书ID
            String sID = "FangYQHL191224.y.1102";

            //提交类报文明文xml包
            String sContentSign = "<?xml version=\"1.0\"encoding=\"GBK\"?>  \n" +
                    "                    <CMS>  \n" +
                    "                     <eb>  \n" +
                    "                      <pub>  \n" +
                    "                       <TransCode>PAYENT</TransCode>  \n" +
                    "                       <CIS>110290001987990</CIS>  \n" +
                    "                       <BankCode>102</BankCode>  \n" +
                    "                       <ID>FangYQHL191224.y.1102</ID>  \n" +
                    "                       <TranDate>20200701</TranDate> \n" +
                    "                       <TranTime>092008123</TranTime> \n " +
                    "                       <fSeqno>" + sPackageID + "</fSeqno>  \n" +
                    "                      </pub>  \n" +
                    "                      <in>  \n" +
                    "                       <OnlBatF>1</OnlBatF>  \n" +
                    "                       <SettleMode>0</SettleMode>  \n" +
                    "                       <TotalNum>1</TotalNum>  \n" +
                    "                       <TotalAmt>1</TotalAmt>  \n" +
                    "                       <SignTime>20200702112808123</SignTime>  \n" +
                    "                       <ReqReserved1></ReqReserved1>  \n" +
                    "                       <ReqReserved2></ReqReserved2>  \n" +
                    "                       <rd>  \n" +
                    "                        <iSeqno>11111</iSeqno>  \n" +
                    "                        <ReimburseNo></ReimburseNo>  \n" +
                    "                        <ReimburseNum></ReimburseNum>  \n" +
                    "                        <StartDate></StartDate>  \n" +
                    "                        <StartTime></StartTime>  \n" +
                    "                        <PayType>1</PayType>  \n" +
                    "                        <PayAccNo>1102021929000036344</PayAccNo>  \n" +
                    "                        <PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>  \n" +
                    "                        <PayAccNameEN></PayAccNameEN>  \n" +
                    "                        <RecAccNo>1102022009000767169</RecAccNo>  \n" +
                    "                        <RecAccNameCN>强貌坡迄匆末魏输移氯零闸涛建桂迄</RecAccNameCN>  \n" +
                    "                        <RecAccNameEN></RecAccNameEN>  \n" +
                    "                        <SysIOFlg>1</SysIOFlg>  \n" +
                    "                        <IsSameCity>1</IsSameCity>  \n" +
                    "                        <Prop></Prop>  \n" +
                    "                        <RecICBCCode></RecICBCCode>  \n" +
                    "                        <RecCityName>工行系统内无需注明</RecCityName>  \n" +
                    "                        <RecBankNo></RecBankNo>  \n" +
                    "                        <RecBankName>工行系统内无需注明</RecBankName>  \n" +
                    "                        <CurrType>001</CurrType>  \n" +
                    "                        <PayAmt>1</PayAmt>  \n" +
                    "                        <UseCode></UseCode>  \n" +
                    "                        <UseCN>上线测试</UseCN>  \n" +
                    "                        <EnSummary></EnSummary>  \n" +
                    "                        <PostScript></PostScript>  \n" +
                    "                        <Summary></Summary>  \n" +
                    "                        <Ref>%fSeqno%</Ref>  \n" +
                    "                        <Oref></Oref>  \n" +
                    "                        <ERPSqn></ERPSqn>  \n" +
                    "                        <BusCode></BusCode>  \n" +
                    "                        <ERPcheckno></ERPcheckno>  \n" +
                    "                        <CrvouhType></CrvouhType>  \n" +
                    "                        <CrvouhName></CrvouhName>  \n" +
                    "                        <CrvouhNo></CrvouhNo>  \n" +
                    "                        <BankType></BankType>  \n" +
                    "                        <FileNames></FileNames>  \n" +
                    "                        <Indexs></Indexs>  \n" +
                    "                        <PaySubNo></PaySubNo>  \n" +
                    "                        <RecSubNo></RecSubNo>  \n" +
                    "                        <MCardNo></MCardNo>  \n" +
                    "                        <MCardName></MCardName>  \n" +
                    "                       </rd>  \n" +
                    "                      </in>  \n" +
                    "                     </eb>  \n" +
                    "                    </CMS> ;\n";
            //NCPort2 签名端口号
            java.net.URL aURL = new java.net.URL("http://" + NCIp + ":" + NCPort2);
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);

            urlConnection.setRequestProperty("Content-Length", String.valueOf(sContentSign.getBytes(sCoding).length));
            urlConnection.setRequestProperty("Content-Type", "INFOSEC_SIGN/1.0");
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(sContentSign.getBytes(sCoding));
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
            System.out.println("repSignContent=="+repSignContent);

            String urlStr1 = "http://" + NCIp + ":" + NCPort + "/servlet/ICBCCMPAPIReqServlet?PackageID=" + sPackageID + "&SendTime=" + QueryDataUtil.getTimeString();
            //构建http客户端
            HttpClient myClient = new HttpClient();
            //加密端口
            PostMethod myPost = new PostMethod(urlStr1);

            myPost.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=GBK");

            myPost.addParameter("Version", cmpVersion);
            myPost.addParameter("TransCode", sTransCode);
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
                System.out.println("******************************NC back******************************\n");
                System.out.println(new String(postResult));

                byte[] decodeResult = getFromBASE64(postResult);
                String sysout = new String(decodeResult, sCoding);

                System.out.println("******************************back data******************************\n");
                System.out.println(sysout);

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
    }

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
