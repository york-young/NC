package com;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewSign {

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
            String sPackageID = "11111122211";
            //交易代码
            String sTransCode = "PAYPER";
            //银行编码
            String sBankCode = "102";
            //集团CIS号
            String sGroupCIS = "110290001987990";
            //证书ID
            String sID = "FangYQHL191224.y.1102";

            //提交类报文明文xml包
            String sContentSign = "<?xml version=\"1.0\" encoding = \"GBK\"?>\n" +
                    "\t<CMS>\n" +
                    "\t\t<eb>\n" +
                    "\t\t\t<pub>\n" +
                    "\t\t\t\t<TransCode>PAYPER</TransCode>\n" +
                    "\t\t\t\t<CIS>110290001987990</CIS>\n" +
                    "\t\t\t\t<BankCode>102</BankCode>\n" +
                    "\t\t\t\t<ID>FangYQHL191224.y.1102</ID>\t\n" +
                    "\t\t\t\t<TranDate>20200701</TranDate>\n" +
                    "\t\t\t\t<TranTime>151511234</TranTime>\n" +
                    "\t\t\t\t<fSeqno>11111122211</fSeqno>\n" +
                    "\t\t\t</pub>\n" +
                    "\t\t\t<in>\n" +
                    "\t\t\t\t<OnlBatF>1</OnlBatF>\n" +
                    "\t\t\t\t<SettleMode>2</SettleMode>\n" +
                    "\t\t\t\t<TotalNum>2</TotalNum>\n" +
                    "\t\t\t\t<TotalAmt>2</TotalAmt>\n" +
                    "\t\t\t\t<SignTime>20200701163911234</SignTime>\n" +
                    "\t\t\t\t<THBaseAccFlag>0</THBaseAccFlag>\n" +
                    "\t\t\t\t<RegSerialNO></RegSerialNO>\t\n" +
                    "\t\t\t\t<PackageName></PackageName>\n" +
                    "\t\t\t\t<TotalSummary></TotalSummary>\n" +
                    "\t\t\t\t<BatchSumFlag></BatchSumFlag>\n" +
                    "\t\t\t\t<rd>\n" +
                    "\t\t\t\t\t<iSeqno>12211</iSeqno>\n" +
                    "\t\t\t\t\t<ReimburseNo></ReimburseNo>\n" +
                    "\t\t\t\t\t<ReimburseNum></ReimburseNum>\n" +
                    "\t\t\t\t\t<StartDate></StartDate>\n" +
                    "\t\t\t\t\t<StartTime></StartTime>\n" +
                    "\t\t\t\t\t<PayType></PayType>\n" +
                    "\t\t\t\t\t<PayAccNo>1102021929000036344</PayAccNo>\n" +
                    "\t\t\t\t\t<PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>\n" +
                    "\t\t\t\t\t<PayAccNameEN></PayAccNameEN>\n" +
                    "\t\t\t\t\t<RecAccNo>1102022009000767169</RecAccNo>\n" +
                    "\t\t\t\t\t<RecAccNameCN>强貌坡迄匆末魏输移氯零闸涛建桂迄</RecAccNameCN>\n" +
                    "\t\t\t\t\t<RecAccNameEN></RecAccNameEN>\n" +
                    "\t\t\t\t\t<SysIOFlg></SysIOFlg>\n" +
                    "\t\t\t\t\t<IsSameCity></IsSameCity>\n" +
                    "\t\t\t\t\t<RecICBCCode></RecICBCCode>\n" +
                    "\t\t\t\t\t<RecCityName></RecCityName>\n" +
                    "\t\t\t\t\t<RecBankNo></RecBankNo>\n" +
                    "\t\t\t\t\t<RecBankName></RecBankName>\n" +
                    "\t\t\t\t\t<CurrType>001</CurrType>\n" +
                    "\t\t\t\t\t<PayAmt>1</PayAmt>\n" +
                    "\t\t\t\t\t<UseCode></UseCode>\n" +
                    "\t\t\t\t\t<UseCN>用途中文描述</UseCN>\n" +
                    "\t\t\t\t\t<EnSummary></EnSummary>\n" +
                    "\t\t\t\t\t<PostScript></PostScript>\n" +
                    "\t\t\t\t\t<Summary></Summary>\n" +
                    "\t\t\t\t\t<Ref></Ref>\n" +
                    "\t\t\t\t\t<Oref></Oref>\n" +
                    "\t\t\t\t\t<ERPSqn></ERPSqn>\n" +
                    "\t\t\t\t\t<BusCode></BusCode>\n" +
                    "\t\t\t\t\t<ERPcheckno></ERPcheckno>\n" +
                    "\t\t\t\t\t<CrvouhType></CrvouhType>\n" +
                    "\t\t\t\t\t<CrvouhName></CrvouhName>\n" +
                    "\t\t\t\t\t<CrvouhNo></CrvouhNo>\n" +
                    "\t\t\t\t\t<MCardNo></MCardNo>\n" +
                    "\t\t\t\t\t<MCardName></MCardName>\t\n" +
                    "\t\t\t\t</rd>\n" +
                    "\t\t\t\t<rd>\n" +
                    "\t\t\t\t\t<iSeqno>111</iSeqno>\n" +
                    "\t\t\t\t\t<ReimburseNo></ReimburseNo>\n" +
                    "\t\t\t\t\t<ReimburseNum></ReimburseNum>\n" +
                    "\t\t\t\t\t<StartDate></StartDate>\n" +
                    "\t\t\t\t\t<StartTime></StartTime>\n" +
                    "\t\t\t\t\t<PayType></PayType>\n" +
                    "\t\t\t\t\t<PayAccNo>1102021929000036344</PayAccNo>\n" +
                    "\t\t\t\t\t<PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>\n" +
                    "\t\t\t\t\t<PayAccNameEN></PayAccNameEN>\n" +
                    "\t\t\t\t\t<RecAccNo>1102027009913534215</RecAccNo>\n" +
                    "\t\t\t\t\t<RecAccNameCN>强貌坡迄匆末魏输移氯樱圾婆桂迄</RecAccNameCN>\n" +
                    "\t\t\t\t\t<RecAccNameEN></RecAccNameEN>\n" +
                    "\t\t\t\t\t<SysIOFlg></SysIOFlg>\n" +
                    "\t\t\t\t\t<IsSameCity></IsSameCity>\n" +
                    "\t\t\t\t\t<RecICBCCode></RecICBCCode>\n" +
                    "\t\t\t\t\t<RecCityName></RecCityName>\n" +
                    "\t\t\t\t\t<RecBankNo></RecBankNo>\n" +
                    "\t\t\t\t\t<RecBankName></RecBankName>\n" +
                    "\t\t\t\t\t<CurrType>001</CurrType>\n" +
                    "\t\t\t\t\t<PayAmt>1</PayAmt>\n" +
                    "\t\t\t\t\t<UseCode></UseCode>\n" +
                    "\t\t\t\t\t<UseCN></UseCN>\n" +
                    "\t\t\t\t\t<EnSummary></EnSummary>\n" +
                    "\t\t\t\t\t<PostScript></PostScript>\n" +
                    "\t\t\t\t\t<Summary></Summary>\n" +
                    "\t\t\t\t\t<Ref></Ref>\n" +
                    "\t\t\t\t\t<Oref></Oref>\n" +
                    "\t\t\t\t\t<ERPSqn></ERPSqn>\n" +
                    "\t\t\t\t\t<BusCode></BusCode>\n" +
                    "\t\t\t\t\t<ERPcheckno></ERPcheckno>\n" +
                    "\t\t\t\t\t<CrvouhType></CrvouhType>\n" +
                    "\t\t\t\t\t<CrvouhName></CrvouhName>\n" +
                    "\t\t\t\t\t<CrvouhNo></CrvouhNo>\n" +
                    "\t\t\t\t\t<MCardNo></MCardNo>\n" +
                    "\t\t\t\t\t<MCardName></MCardName>\t\n" +
                    "\t\t\t\t</rd>\n" +
                    "\t\t\t</in>\n" +
                    "\t\t</eb>\n" +
                    "</CMS>";
            //NCPort2 签名端口号
            java.net.URL aURL = new java.net.URL("http://" + NCIp + ":" + NCPort2);
            //打开和URL之间的连接
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            //设置通用的请求属性
            urlConnection.setRequestProperty("Content-Length", String.valueOf(sContentSign.getBytes(sCoding).length));
            urlConnection.setRequestProperty("Content-Type", "INFOSEC_SIGN/1.0");
//            System.out.println(new String(sContentSign, ));
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
