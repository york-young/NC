package com;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
        //String NCIp = "10.193.48.105";
        String NCIp = "192.168.0.115";
        // NC 加密端口号
        String NCPort = "7070";
        // 签名端口号
        String NCPort2 = "7080";
        //是否对交易签名
        boolean signatureflg = true;

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
            String sPackageID = QueryDataUtil.getRandom(15);
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
            System.out.println(responseCode);
            if (responseCode != 200) {
                System.out.println("NC  failed");
            }
            //NC签名结果
            String rmg = urlConnection.getResponseMessage();
            System.out.println(rmg);
            StringBuffer repContent = new StringBuffer();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);

            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                repContent.append(readLine);
            }
            in.close();
            urlConnection.disconnect();

            String repSignContent = null;

            repSignContent = repContent.toString().split("<sign>")[1].split("</sign>")[0];

            if (null == repSignContent || "".equals(repSignContent)) {
                System.out.println("银行无返回数据");
                return;
            }
            //String repSignContent = "MIILAAYJKoZIhvcNAQcCoIIK8TCCCu0CAQExCzAJBgUrDgMCGgUAMIIFxAYJKoZIhvcNAQcBoIIFtQSCBbE8P3htbCB2ZXJzaW9uPSIxLjAiIGVuY29kaW5nPSJHQksiPz48Q01TPjxlYj48cHViPjxUcmFuc0NvZGU+UEFZRU5UPC9UcmFuc0NvZGU+PENJUz4xMTAyOTAwMDE5ODc5OTA8L0NJUz48QmFua0NvZGU+MTAyPC9CYW5rQ29kZT48SUQ+RmFuZ1lRSEwxOTEyMjQueS4xMTAyPC9JRD48VHJhbkRhdGU+bnVsbDwvVHJhbkRhdGU+PFRyYW5UaW1lPjE2MTExOTI5NjwvVHJhblRpbWU+PGZTZXFubz5TWTIwMjAwNzIxMDAxODwvZlNlcW5vPjwvcHViPjxpbj48T25sQmF0Rj4xPC9PbmxCYXRGPjxTZXR0bGVNb2RlPjA8L1NldHRsZU1vZGU+PFRvdGFsTnVtPjE8L1RvdGFsTnVtPjxUb3RhbEFtdD4xMDA8L1RvdGFsQW10PjxTaWduVGltZT5udWxsMTYxMTE5NTE5PC9TaWduVGltZT48UmVxUmVzZXJ2ZWQxPjwvUmVxUmVzZXJ2ZWQxPjxSZXFSZXNlcnZlZDI+PC9SZXFSZXNlcnZlZDI+PEFsZXJ0RmxhZz4xPC9BbGVydEZsYWc+PHJkPjxpU2Vxbm8+MTwvaVNlcW5vPjxSZWltYnVyc2VObz48L1JlaW1idXJzZU5vPjxSZWltYnVyc2VOdW0+PC9SZWltYnVyc2VOdW0+PFN0YXJ0RGF0ZT48L1N0YXJ0RGF0ZT48U3RhcnRUaW1lPjwvU3RhcnRUaW1lPiA8UGF5VHlwZT4wPC9QYXlUeXBlPjxQYXlBY2NObz4yMzIxMzIxMzIxMzIxMzEyPC9QYXlBY2NObz48UGF5QWNjTmFtZUNOPtbQvajI/b7WPC9QYXlBY2NOYW1lQ04+PFBheUFjY05hbWVFTj48L1BheUFjY05hbWVFTj48UmVjQWNjTm8+NTMxNzAwMDIzMDAyNTQzMjwvUmVjQWNjTm8+PFJlY0FjY05hbWVDTj7W0L2oyP2+1tPQz965q8u+PC9SZWNBY2NOYW1lQ04+IDxSZWNBY2NOYW1lRU4+PC9SZWNBY2NOYW1lRU4+IDxTeXNJT0ZsZz4wPC9TeXNJT0ZsZz48SXNTYW1lQ2l0eT4wPC9Jc1NhbWVDaXR5PjxQcm9wPjA8L1Byb3A+PFJlY0lDQkNDb2RlPjwvUmVjSUNCQ0NvZGU+PFJlY0NpdHlOYW1lPjwvUmVjQ2l0eU5hbWU+PFJlY0JhbmtObz48L1JlY0JhbmtObz48UmVjQmFua05hbWU+PC9SZWNCYW5rTmFtZT48Q3VyclR5cGU+MDAxPC9DdXJyVHlwZT48UGF5QW10PjEwMDwvUGF5QW10PjxVc2VDb2RlPjwvVXNlQ29kZT48VXNlQ04+PC9Vc2VDTj48RW5TdW1tYXJ5PjwvRW5TdW1tYXJ5PjxQb3N0U2NyaXB0PjwvUG9zdFNjcmlwdD48U3VtbWFyeT48L1N1bW1hcnk+PFJlZj48L1JlZj48T3JlZj48L09yZWY+PEVSUFNxbj48L0VSUFNxbj48QnVzQ29kZT48L0J1c0NvZGU+PEVSUGNoZWNrbm8+PC9FUlBjaGVja25vPjxDcnZvdWhUeXBlPjwvQ3J2b3VoVHlwZT48Q3J2b3VoTmFtZT48L0Nydm91aE5hbWU+PENydm91aE5vPjwvQ3J2b3VoTm8+PEJhbmtUeXBlPjwvQmFua1R5cGU+PEZpbGVOYW1lcz48L0ZpbGVOYW1lcz48SW5kZXhzPjwvSW5kZXhzPjxQYXlTdWJObz48L1BheVN1Yk5vPjxSZWNTdWJObz48L1JlY1N1Yk5vPjxNQ2FyZE5vPjwvTUNhcmRObz48TUNhcmROYW1lPjwvTUNhcmROYW1lPjwvcmQ+PC9pbj48L2ViPjwvQ01TPqCCA50wggOZMIICgaADAgECAgobksoQJVYAAueWMA0GCSqGSIb3DQEBCwUAMDsxHzAdBgNVBAMTFklDQkMgVGVzdCBDb3Jwb3JhdGUgQ0ExGDAWBgNVBAoTD3Rlc3RpY2JjLmNvbS5jbjAeFw0xOTEyMzEwMDQzNDRaFw0yNDEyMzEwMDQzNDRaMEkxHjAcBgNVBAMMFUZhbmdZUUhMMTkxMjI0LnkuMTEwMjENMAsGA1UECwwEMTEwMjEYMBYGA1UECgwPdGVzdGljYmMuY29tLmNuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxie6v2wUmh2z0CDMX/JA5KVMSDSWF5v8jZa/piEguRoxv0L7J2iONm6nCiIuwdJlVpH+0kNinOvyTjiq0SoZo85CfwxZiWU8cJuMMp70uusIhcyv8v9Cuuqn3gzpLWZGeU8tgUkq/R0dN1C0xr10eDq/ET+Sh1RLP1rLhmuioqWPPrLluT41Qh43cIiqAyv2d932GoArtMSjtT4v68w6tMDkFrDgZZ1EOEaPRXzzXPObfkBPUHpQF05J0sGykr5OAd7hrg4psseAu3DM6pUz6x4es/mugkfizeZiZM+4aOqhEkMwOz5Ffzd3fg6hBBH7k60wscvCON9ii0lSpoe3LQIDAQABo4GQMIGNMB8GA1UdIwQYMBaAFER9t5AsN6TZ7WzipIdXZwq18E0UMEsGA1UdHwREMEIwQKA+oDykOjA4MQ4wDAYDVQQDDAVjcmwzOTEMMAoGA1UECwwDY3JsMRgwFgYDVQQKDA90ZXN0aWNiYy5jb20uY24wHQYDVR0OBBYEFMvolMUNWknFgH2IwdXHUPU/x+LXMA0GCSqGSIb3DQEBCwUAA4IBAQBII02vkQeL2mvyx84ghW0ezOrOZYLlQKC4+UiETVcI1GkcoQ9U8eRSfbMsIMFfTS5J6TnQw8Q9VF5qLNy7dN5pQvir4x0kULYrbMrPxv7QN/XIZOmAYALDqrBZm9e2oi6q3NrFEWtYG7Wj/2InJNPvFmmbsXAVTIjQNuwr8Dotlg+OgO8gsJrgiCdtF7lZHWgNU7w3HIbMrLv5wY9fzTVYgcQ1v9VwGFM4CYoleppeuV0zglwizgvvwlzMEtvnYnpACHLOrmLxYq9sAZmTQwZmbFu6VzBx3EwTRuJgXj4gF4F/w1xbI0MXN44Olef5u8wpuHERRvSUOOfhoO+AuD6kMYIBcDCCAWwCAQEwSTA7MR8wHQYDVQQDExZJQ0JDIFRlc3QgQ29ycG9yYXRlIENBMRgwFgYDVQQKEw90ZXN0aWNiYy5jb20uY24CChuSyhAlVgAC55YwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASCAQC6SGcvzxm1RYywlkc63JJ/cfQSIqE3r9IqIqqwYfHDQpQ58dI441bOVm69JGzhkV8afM2yAmfPzOeZ//tiATjjhDpmNzQNfbZeqqU3fH/YaZu+dmPI7Sv8GPBitF9/OsUGo+nunXYJZJ+uMNFW0w/wjXjAqeU7lW3g2lZuSz5A/EMMtlkF97Iai4XV155J6b5mQZ4ZVuaB7e58Lp+1ahk/0LbbfddxEiiep4iUirtRSg8aIANr4OqIq1/p3Pwd4FuIpx4FDvFjEFGDZAd4sRxwoTLLFonNjCcB5jGTpxWGOFbvuGXY/Koxt6GzpyUb++2fgVE+kNrdSc+sAZWIEV6C";
            System.out.println("SignContent==" + repSignContent);

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
            //myPost.addParameter("reqData", repSignContent);

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
            System.out.println("IOException:yichang");
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
            System.err.println("error:" + e.getMessage());
            System.out.println("Exception:yichang");
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
