package com;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NCExample {

    public static void main(String[] args) throws Exception {

        // NC IP地址
        String NCIp = "192.168.0.115";
        // NC 加密端口号
        String NCPort = "7070";
        // 签名端口号
        String NCPort2 = "7080";

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
            String sPackageID = "22222221111111";
            //交易代码
            String sTransCode = "PAYENT";
            //银行编码
            String sBankCode = "102";
            //集团CIS号
            String sGroupCIS = "110290001987990";
            //证书ID
            String sID = "FangYQHL191224.y.1102";

            //提交类报文明文xml包


            StringBuilder sContentSign = new StringBuilder();
            sContentSign.append("<?xml version=\"1.0\"encoding=\"GBK\"?>" +
                    "                  <CMS><eb><pub>" +
                    "                       <TransCode>PAYENT</TransCode>  " +
                    "                       <CIS>110290001987990</CIS>  " +
                    "                       <BankCode>102</BankCode>  " +
                    "                       <ID>FangYQHL191224.y.1102</ID>  " +
                    "                       <TranDate>20200701</TranDate> " +
                    "                       <TranTime>131508123</TranTime>  " +
                    "                       <fSeqno>22222221111111</fSeqno>  " +
                    "                      </pub>  " +
                    "                      <in>  " +
                    "                       <OnlBatF>1</OnlBatF>  " +
                    "                       <SettleMode>0</SettleMode>  " +
                    "                       <TotalNum>2</TotalNum>  " +
                    "                       <TotalAmt>2</TotalAmt>  " +
                    "                       <SignTime>20200701170208123</SignTime>  " +
                    "                       <ReqReserved1></ReqReserved1>  " +
                    "                       <ReqReserved2></ReqReserved2>");

            sContentSign.append("<rd>" +
                    "                        <iSeqno>11111</iSeqno>  " +
                    "                        <ReimburseNo></ReimburseNo>  " +
                    "                        <ReimburseNum></ReimburseNum>  " +
                    "                        <StartDate></StartDate>  " +
                    "                        <StartTime></StartTime>  " +
                    "                        <PayType>1</PayType>  " +
                    "                        <PayAccNo>1102021929000036344</PayAccNo>  " +
                    "                        <PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>  " +
                    "                        <PayAccNameEN></PayAccNameEN>  " +
                    "                        <RecAccNo>1102022009000767169</RecAccNo>  " +
                    "                        <RecAccNameCN>强貌坡迄匆末魏输移氯零闸涛建桂迄</RecAccNameCN>  " +
                    "                        <RecAccNameEN></RecAccNameEN>  " +
                    "                        <SysIOFlg>1</SysIOFlg>  " +
                    "                        <IsSameCity>1</IsSameCity>  " +
                    "                        <Prop></Prop>  " +
                    "                        <RecICBCCode></RecICBCCode>  " +
                    "                        <RecCityName>工行系统内无需注明</RecCityName>  " +
                    "                        <RecBankNo></RecBankNo>  " +
                    "                        <RecBankName>工行系统内无需注明</RecBankName>  " +
                    "                        <CurrType>001</CurrType>  " +
                    "                        <PayAmt>1</PayAmt>  " +
                    "                        <UseCode></UseCode>  " +
                    "                        <UseCN>上线测试</UseCN>  " +
                    "                        <EnSummary></EnSummary>  " +
                    "                        <PostScript></PostScript>  " +
                    "                        <Summary></Summary>  " +
                    "                        <Ref></Ref>  " +
                    "                        <Oref></Oref>  " +
                    "                        <ERPSqn></ERPSqn>  " +
                    "                        <BusCode></BusCode>  " +
                    "                        <ERPcheckno></ERPcheckno>  " +
                    "                        <CrvouhType></CrvouhType>  " +
                    "                        <CrvouhName></CrvouhName>  " +
                    "                        <CrvouhNo></CrvouhNo>  " +
                    "                        <BankType></BankType>  " +
                    "                        <FileNames></FileNames>  " +
                    "                        <Indexs></Indexs>  " +
                    "                        <PaySubNo></PaySubNo>  " +
                    "                        <RecSubNo></RecSubNo>  " +
                    "                        <MCardNo></MCardNo>  " +
                    "                        <MCardName></MCardName>  " +
                    "                       </rd>" );
            sContentSign.append("<rd>" +
                    "                        <iSeqno>111111111111111</iSeqno>  " +
                    "                        <ReimburseNo></ReimburseNo>  " +
                    "                        <ReimburseNum></ReimburseNum>  " +
                    "                        <StartDate></StartDate>  " +
                    "                        <StartTime></StartTime>  " +
                    "                        <PayType>1</PayType>  " +
                    "                        <PayAccNo>1102021929000036344</PayAccNo>  " +
                    "                        <PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>  " +
                    "                        <PayAccNameEN></PayAccNameEN>  " +
                    "                        <RecAccNo>1102024009000257463</RecAccNo>  " +
                    "                        <RecAccNameCN>强貌坡迄匆末魏输移氯用僚桂迄</RecAccNameCN>  " +
                    "                        <RecAccNameEN></RecAccNameEN>  " +
                    "                        <SysIOFlg>1</SysIOFlg>  " +
                    "                        <IsSameCity>1</IsSameCity>  " +
                    "                        <Prop></Prop>  " +
                    "                        <RecICBCCode></RecICBCCode>  " +
                    "                        <RecCityName>工行系统内无需注明</RecCityName>  " +
                    "                        <RecBankNo></RecBankNo>  " +
                    "                        <RecBankName>工行系统内无需注明</RecBankName>  " +
                    "                        <CurrType>001</CurrType>  " +
                    "                        <PayAmt>1</PayAmt>  " +
                    "                        <UseCode></UseCode>  " +
                    "                        <UseCN>上线测试</UseCN>  " +
                    "                        <EnSummary></EnSummary>  " +
                    "                        <PostScript></PostScript>  " +
                    "                        <Summary></Summary>  " +
                    "                        <Ref>%fSeqno%</Ref>  " +
                    "                        <Oref></Oref>  " +
                    "                        <ERPSqn></ERPSqn>  " +
                    "                        <BusCode></BusCode>  " +
                    "                        <ERPcheckno></ERPcheckno>  " +
                    "                        <CrvouhType></CrvouhType>  " +
                    "                        <CrvouhName></CrvouhName>  " +
                    "                        <CrvouhNo></CrvouhNo>  " +
                    "                        <BankType></BankType>  " +
                    "                        <FileNames></FileNames>  " +
                    "                        <Indexs></Indexs>  " +
                    "                        <PaySubNo></PaySubNo>  " +
                    "                        <RecSubNo></RecSubNo>  " +
                    "                        <MCardNo></MCardNo>  " +
                    "                        <MCardName></MCardName>  " +
                    "                       </rd>" );
             sContentSign.append("</in></eb></CMS>");       
                    
            //NCPort2 签名端口号
            URL aURL = new URL("http://" + NCIp + ":" + NCPort2);
            //打开和URL之间的连接
            HttpURLConnection urlConnection = (HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            //设置通用的请求属性
            //System.out.println("sContentSign======"+ new String(sContentSign.getBytes(), "GBK"));
            urlConnection.setRequestProperty("Content-Length", String.valueOf(sContentSign.toString().getBytes(sCoding).length));
            urlConnection.setRequestProperty("Content-Type", "INFOSEC_SIGN/1.0");
//            urlConnection.setRequestProperty("accept", "*/*");
//            urlConnection.setRequestProperty("connection", "Keep-Alive");
//            urlConnection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立实际的连接
            //urlConnection.connect();

            //获取URLConnection对象对应的输出流
            //PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //发送请求参数
            //out.print(sContentSign);
            //byte[] bytes = sContentSign.getBytes(sCoding);
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
            myPost.addParameter("Cert", "");
            myPost.addParameter("Language", sLanguage);
            myPost.addParameter("zipFlag", sZip);
            myPost.addParameter("reqData", repSignContent);

            System.out.println("start send jiami..." + System.currentTimeMillis());
            //获得http返回码
            System.out.println(myClient);

            int returnFlag = myClient.executeMethod(myPost);

            System.out.println(returnFlag);

            try {
                String postResult = myPost.getResponseBodyAsString();
                System.out.println("postResult"+postResult);
                if (postResult.startsWith("reqData=")) {
                    postResult = postResult.substring(8);
                }
                System.out.println("******************************NC back******************************");
                System.out.println(new String(postResult));

                byte[] decodeResult = getFromBASE64(postResult);
                String sysout = new String(decodeResult, sCoding);

                System.out.println("******************************back data******************************");
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
