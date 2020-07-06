package com;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewSign {
    // NC IP地址
    String NCIp = "192.168.0.115";
    // NC 加密端口号
    String NCPort = "7070";
    // 签名端口号
    String NCPort2 = "7080";
    //是否对交易签名
    //编码格式
    String sCoding = "GBK";
    //接口版本 0.0.0.1或者0.0.1.0
    String cmpVersion = "0.0.1.0";
    //是否大文件方式
    String sZip = "0";
    String sLanguage = "zh_CN";
    //下面字段与明文xml包中保持一致
    //包序列号与xml包中保持一致
    String sPackageID = "111611122111211";
    //交易代码
    String sTransCode = "PAYPER";
    //银行编码
    String sBankCode = "102";
    //集团CIS号
    String sGroupCIS = "110290001987990";
    //证书ID
    String sID = "FangYQHL191224.y.1102";

    @Test
    public void test() {
        try {
            //提交类报文明文xml包
            String sContentSign = "<?xml version=\"1.0\" encoding = \"GBK\"?>\n" +
                    "\t<CMS>\n" +
                    "\t\t<eb>\n" +
                    "\t\t\t<pub>\n" +
                    "\t\t\t\t<TransCode>PAYPER</TransCode>\n" +
                    "\t\t\t\t<CIS>110290001987990</CIS>\n" +
                    "\t\t\t\t<BankCode>102</BankCode>\n" +
                    "\t\t\t\t<ID>FangYQHL191224.y.1102</ID>\t\n" +
                    "\t\t\t\t<TranDate>20200702</TranDate>\n" +
                    "\t\t\t\t<TranTime>151511234</TranTime>\n" +
                    "\t\t\t\t<fSeqno>111611122111211</fSeqno>\n" +
                    "\t\t\t</pub>\n" +
                    "\t\t\t<in>\n" +
                    "\t\t\t\t<OnlBatF>1</OnlBatF>\n" +
                    "\t\t\t\t<SettleMode>2</SettleMode>\n" +
                    "\t\t\t\t<TotalNum>2</TotalNum>\n" +
                    "\t\t\t\t<TotalAmt>2</TotalAmt>\n" +
                    "\t\t\t\t<SignTime>20200702092611234</SignTime>\n" +
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
            String repSignContent = Signature(sContentSign.toString());
            //String sysout = DirectiveSubmission("PAYPER", repSignContent);
            //System.out.println("sysout++++++++"+sysout);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getFromBASE64(String s) {
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

    /**
     * @Description:
     *             数据签名的方法
     * @Author: York
     * @Date: 2020/7/6 9:02
     * @param sContentSign
     * @Return: java.lang.String
     **/
    public String Signature(String sContentSign) throws Exception {
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
        //System.out.println(repSignContent);
        return repSignContent;
    }
    /**
     * @Description:
     *             指令提交方法
     * @Author: York
     * @Date: 2020/7/6 9:01
     * @param sTransCode
     * @param repSignContent
     * @Return: java.lang.String
     **/
    @Test
    public void DirectiveSubmission() throws IOException {
        String sTransCode="PAYPER";
        String repSignContent="MIIZlAYJKoZIhvcNAQcCoIIZhTCCGYECAQExCzAJBgUrDgMCGgUAMIIUWAYJKoZIhvcNAQcBoIIUSQSCFEU8P3htbCB2ZXJzaW9uPSIxLjAiZW5jb2Rpbmc9IkdCSyI/PiAgICAgICAgICAgICAgICAgIDxDTVM+PGViPjxwdWI+ICAgICAgICAgICAgICAgICAgICAgICA8VHJhbnNDb2RlPlBBWUVOVDwvVHJhbnNDb2RlPiAgICAgICAgICAgICAgICAgICAgICAgICA8Q0lTPjExMDI5MDAwMTk4Nzk5MDwvQ0lTPiAgICAgICAgICAgICAgICAgICAgICAgICA8QmFua0NvZGU+MTAyPC9CYW5rQ29kZT4gICAgICAgICAgICAgICAgICAgICAgICAgPElEPkZhbmdZUUhMMTkxMjI0LnkuMTEwMjwvSUQ+ICAgICAgICAgICAgICAgICAgICAgICAgIDxUcmFuRGF0ZT4yMDIwMDcwMTwvVHJhbkRhdGU+ICAgICAgICAgICAgICAgICAgICAgICAgPFRyYW5UaW1lPjEzMTUwODEyMzwvVHJhblRpbWU+ICAgICAgICAgICAgICAgICAgICAgICAgIDxmU2Vxbm8+MjIyMjIyMjExMTExMTE8L2ZTZXFubz4gICAgICAgICAgICAgICAgICAgICAgICA8L3B1Yj4gICAgICAgICAgICAgICAgICAgICAgICA8aW4+ICAgICAgICAgICAgICAgICAgICAgICAgIDxPbmxCYXRGPjE8L09ubEJhdEY+ICAgICAgICAgICAgICAgICAgICAgICAgIDxTZXR0bGVNb2RlPjA8L1NldHRsZU1vZGU+ICAgICAgICAgICAgICAgICAgICAgICAgIDxUb3RhbE51bT4yPC9Ub3RhbE51bT4gICAgICAgICAgICAgICAgICAgICAgICAgPFRvdGFsQW10PjI8L1RvdGFsQW10PiAgICAgICAgICAgICAgICAgICAgICAgICA8U2lnblRpbWU+MjAyMDA3MDExNzAyMDgxMjM8L1NpZ25UaW1lPiAgICAgICAgICAgICAgICAgICAgICAgICA8UmVxUmVzZXJ2ZWQxPjwvUmVxUmVzZXJ2ZWQxPiAgICAgICAgICAgICAgICAgICAgICAgICA8UmVxUmVzZXJ2ZWQyPjwvUmVxUmVzZXJ2ZWQyPjxyZD4gICAgICAgICAgICAgICAgICAgICAgICA8aVNlcW5vPjExMTExPC9pU2Vxbm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVpbWJ1cnNlTm8+PC9SZWltYnVyc2VObz4gICAgICAgICAgICAgICAgICAgICAgICAgIDxSZWltYnVyc2VOdW0+PC9SZWltYnVyc2VOdW0+ICAgICAgICAgICAgICAgICAgICAgICAgICA8U3RhcnREYXRlPjwvU3RhcnREYXRlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFN0YXJ0VGltZT48L1N0YXJ0VGltZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQYXlUeXBlPjE8L1BheVR5cGU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UGF5QWNjTm8+MTEwMjAyMTkyOTAwMDAzNjM0NDwvUGF5QWNjTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UGF5QWNjTmFtZUNOPse/w7LGwsb5tNLEqc66yuTSxsLIwePVos3Dxvk8L1BheUFjY05hbWVDTj4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQYXlBY2NOYW1lRU4+PC9QYXlBY2NOYW1lRU4+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjQWNjTm8+MTEwMjAyMjAwOTAwMDc2NzE2OTwvUmVjQWNjTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjQWNjTmFtZUNOPse/w7LGwsb5tNLEqc66yuTSxsLIwePVoszOvai58Mb5PC9SZWNBY2NOYW1lQ04+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjQWNjTmFtZUVOPjwvUmVjQWNjTmFtZUVOPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFN5c0lPRmxnPjE8L1N5c0lPRmxnPiAgICAgICAgICAgICAgICAgICAgICAgICAgPElzU2FtZUNpdHk+MTwvSXNTYW1lQ2l0eT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQcm9wPjwvUHJvcD4gICAgICAgICAgICAgICAgICAgICAgICAgIDxSZWNJQ0JDQ29kZT48L1JlY0lDQkNDb2RlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY0NpdHlOYW1lPrmk0NDPtc2zxNrO3tDo16LD9zwvUmVjQ2l0eU5hbWU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjQmFua05vPjwvUmVjQmFua05vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY0JhbmtOYW1lPrmk0NDPtc2zxNrO3tDo16LD9zwvUmVjQmFua05hbWU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8Q3VyclR5cGU+MDAxPC9DdXJyVHlwZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQYXlBbXQ+MTwvUGF5QW10PiAgICAgICAgICAgICAgICAgICAgICAgICAgPFVzZUNvZGU+PC9Vc2VDb2RlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFVzZUNOPsnPz9+y4srUPC9Vc2VDTj4gICAgICAgICAgICAgICAgICAgICAgICAgIDxFblN1bW1hcnk+PC9FblN1bW1hcnk+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UG9zdFNjcmlwdD48L1Bvc3RTY3JpcHQ+ICAgICAgICAgICAgICAgICAgICAgICAgICA8U3VtbWFyeT48L1N1bW1hcnk+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVmPjwvUmVmPiAgICAgICAgICAgICAgICAgICAgICAgICAgPE9yZWY+PC9PcmVmPiAgICAgICAgICAgICAgICAgICAgICAgICAgPEVSUFNxbj48L0VSUFNxbj4gICAgICAgICAgICAgICAgICAgICAgICAgIDxCdXNDb2RlPjwvQnVzQ29kZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxFUlBjaGVja25vPjwvRVJQY2hlY2tubz4gICAgICAgICAgICAgICAgICAgICAgICAgIDxDcnZvdWhUeXBlPjwvQ3J2b3VoVHlwZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxDcnZvdWhOYW1lPjwvQ3J2b3VoTmFtZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxDcnZvdWhObz48L0Nydm91aE5vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPEJhbmtUeXBlPjwvQmFua1R5cGU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8RmlsZU5hbWVzPjwvRmlsZU5hbWVzPiAgICAgICAgICAgICAgICAgICAgICAgICAgPEluZGV4cz48L0luZGV4cz4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQYXlTdWJObz48L1BheVN1Yk5vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY1N1Yk5vPjwvUmVjU3ViTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8TUNhcmRObz48L01DYXJkTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8TUNhcmROYW1lPjwvTUNhcmROYW1lPiAgICAgICAgICAgICAgICAgICAgICAgICA8L3JkPjxyZD4gICAgICAgICAgICAgICAgICAgICAgICA8aVNlcW5vPjExMTExMTExMTExMTExMTwvaVNlcW5vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlaW1idXJzZU5vPjwvUmVpbWJ1cnNlTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVpbWJ1cnNlTnVtPjwvUmVpbWJ1cnNlTnVtPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFN0YXJ0RGF0ZT48L1N0YXJ0RGF0ZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxTdGFydFRpbWU+PC9TdGFydFRpbWU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UGF5VHlwZT4xPC9QYXlUeXBlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFBheUFjY05vPjExMDIwMjE5MjkwMDAwMzYzNDQ8L1BheUFjY05vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFBheUFjY05hbWVDTj7Hv8OyxsLG+bTSxKnOusrk0sbCyMHj1aLNw8b5PC9QYXlBY2NOYW1lQ04+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UGF5QWNjTmFtZUVOPjwvUGF5QWNjTmFtZUVOPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY0FjY05vPjExMDIwMjQwMDkwMDAyNTc0NjM8L1JlY0FjY05vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY0FjY05hbWVDTj7Hv8OyxsLG+bTSxKnOusrk0sbCyNPDwcW58Mb5PC9SZWNBY2NOYW1lQ04+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjQWNjTmFtZUVOPjwvUmVjQWNjTmFtZUVOPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFN5c0lPRmxnPjE8L1N5c0lPRmxnPiAgICAgICAgICAgICAgICAgICAgICAgICAgPElzU2FtZUNpdHk+MTwvSXNTYW1lQ2l0eT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQcm9wPjwvUHJvcD4gICAgICAgICAgICAgICAgICAgICAgICAgIDxSZWNJQ0JDQ29kZT48L1JlY0lDQkNDb2RlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY0NpdHlOYW1lPrmk0NDPtc2zxNrO3tDo16LD9zwvUmVjQ2l0eU5hbWU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjQmFua05vPjwvUmVjQmFua05vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFJlY0JhbmtOYW1lPrmk0NDPtc2zxNrO3tDo16LD9zwvUmVjQmFua05hbWU+ICAgICAgICAgICAgICAgICAgICAgICAgICA8Q3VyclR5cGU+MDAxPC9DdXJyVHlwZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxQYXlBbXQ+MTwvUGF5QW10PiAgICAgICAgICAgICAgICAgICAgICAgICAgPFVzZUNvZGU+PC9Vc2VDb2RlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFVzZUNOPsnPz9+y4srUPC9Vc2VDTj4gICAgICAgICAgICAgICAgICAgICAgICAgIDxFblN1bW1hcnk+PC9FblN1bW1hcnk+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UG9zdFNjcmlwdD48L1Bvc3RTY3JpcHQ+ICAgICAgICAgICAgICAgICAgICAgICAgICA8U3VtbWFyeT48L1N1bW1hcnk+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVmPiVmU2Vxbm8lPC9SZWY+ICAgICAgICAgICAgICAgICAgICAgICAgICA8T3JlZj48L09yZWY+ICAgICAgICAgICAgICAgICAgICAgICAgICA8RVJQU3FuPjwvRVJQU3FuPiAgICAgICAgICAgICAgICAgICAgICAgICAgPEJ1c0NvZGU+PC9CdXNDb2RlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPEVSUGNoZWNrbm8+PC9FUlBjaGVja25vPiAgICAgICAgICAgICAgICAgICAgICAgICAgPENydm91aFR5cGU+PC9DcnZvdWhUeXBlPiAgICAgICAgICAgICAgICAgICAgICAgICAgPENydm91aE5hbWU+PC9DcnZvdWhOYW1lPiAgICAgICAgICAgICAgICAgICAgICAgICAgPENydm91aE5vPjwvQ3J2b3VoTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8QmFua1R5cGU+PC9CYW5rVHlwZT4gICAgICAgICAgICAgICAgICAgICAgICAgIDxGaWxlTmFtZXM+PC9GaWxlTmFtZXM+ICAgICAgICAgICAgICAgICAgICAgICAgICA8SW5kZXhzPjwvSW5kZXhzPiAgICAgICAgICAgICAgICAgICAgICAgICAgPFBheVN1Yk5vPjwvUGF5U3ViTm8+ICAgICAgICAgICAgICAgICAgICAgICAgICA8UmVjU3ViTm8+PC9SZWNTdWJObz4gICAgICAgICAgICAgICAgICAgICAgICAgIDxNQ2FyZE5vPjwvTUNhcmRObz4gICAgICAgICAgICAgICAgICAgICAgICAgIDxNQ2FyZE5hbWU+PC9NQ2FyZE5hbWU+ICAgICAgICAgICAgICAgICAgICAgICAgIDwvcmQ+PC9pbj48L2ViPjwvQ01TPqCCA50wggOZMIICgaADAgECAgobksoQJVYAAueWMA0GCSqGSIb3DQEBCwUAMDsxHzAdBgNVBAMTFklDQkMgVGVzdCBDb3Jwb3JhdGUgQ0ExGDAWBgNVBAoTD3Rlc3RpY2JjLmNvbS5jbjAeFw0xOTEyMzEwMDQzNDRaFw0yNDEyMzEwMDQzNDRaMEkxHjAcBgNVBAMMFUZhbmdZUUhMMTkxMjI0LnkuMTEwMjENMAsGA1UECwwEMTEwMjEYMBYGA1UECgwPdGVzdGljYmMuY29tLmNuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxie6v2wUmh2z0CDMX/JA5KVMSDSWF5v8jZa/piEguRoxv0L7J2iONm6nCiIuwdJlVpH+0kNinOvyTjiq0SoZo85CfwxZiWU8cJuMMp70uusIhcyv8v9Cuuqn3gzpLWZGeU8tgUkq/R0dN1C0xr10eDq/ET+Sh1RLP1rLhmuioqWPPrLluT41Qh43cIiqAyv2d932GoArtMSjtT4v68w6tMDkFrDgZZ1EOEaPRXzzXPObfkBPUHpQF05J0sGykr5OAd7hrg4psseAu3DM6pUz6x4es/mugkfizeZiZM+4aOqhEkMwOz5Ffzd3fg6hBBH7k60wscvCON9ii0lSpoe3LQIDAQABo4GQMIGNMB8GA1UdIwQYMBaAFER9t5AsN6TZ7WzipIdXZwq18E0UMEsGA1UdHwREMEIwQKA+oDykOjA4MQ4wDAYDVQQDDAVjcmwzOTEMMAoGA1UECwwDY3JsMRgwFgYDVQQKDA90ZXN0aWNiYy5jb20uY24wHQYDVR0OBBYEFMvolMUNWknFgH2IwdXHUPU/x+LXMA0GCSqGSIb3DQEBCwUAA4IBAQBII02vkQeL2mvyx84ghW0ezOrOZYLlQKC4+UiETVcI1GkcoQ9U8eRSfbMsIMFfTS5J6TnQw8Q9VF5qLNy7dN5pQvir4x0kULYrbMrPxv7QN/XIZOmAYALDqrBZm9e2oi6q3NrFEWtYG7Wj/2InJNPvFmmbsXAVTIjQNuwr8Dotlg+OgO8gsJrgiCdtF7lZHWgNU7w3HIbMrLv5wY9fzTVYgcQ1v9VwGFM4CYoleppeuV0zglwizgvvwlzMEtvnYnpACHLOrmLxYq9sAZmTQwZmbFu6VzBx3EwTRuJgXj4gF4F/w1xbI0MXN44Olef5u8wpuHERRvSUOOfhoO+AuD6kMYIBcDCCAWwCAQEwSTA7MR8wHQYDVQQDExZJQ0JDIFRlc3QgQ29ycG9yYXRlIENBMRgwFgYDVQQKEw90ZXN0aWNiYy5jb20uY24CChuSyhAlVgAC55YwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASCAQCieYzmNL9W2xkLScBWD4jnuOjYJWvj+zgMwBrgkwikRR+umUgF5n3RhJMTKqVP9NdAHpu9E6XRW+vR/e7udvg8Te+e6NEZHextDDnrFD79eeBatk9yIITfKgqwq+Mf0o9kXJ7gNofr1BXLI5K5FIlbBpKQasDUzSNPZfKrALE+VKAcH1c0LWxaeaUHyp7Hz6UcXLYpvYLaDh8R5OytvDRPMutyQ/+M0rL9fdbDyTVRnEM2V3xRiN77va5k8/NDznEvnU+eiA0jn6tgimXAoeVn+oaSZf+HzZnO/g6ZOYbdk4WmMsSj+lR4f3BbKY49sh8tiRUiQe2yQvWRD2f+wRFb\n" +
                "";

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
        /**获得http返回码*/

        int returnFlag = myClient.executeMethod(myPost);

        System.out.println(returnFlag);



        try {
            String postResult = myPost.getResponseBodyAsString();
            if (postResult.startsWith("reqData=")) {
                postResult = postResult.substring(8);
            }
            /*System.out.println("******************************NC back******************************\n");
            System.out.println(new String(postResult));*/
            byte[] decodeResult = getFromBASE64(postResult);
            String sysout = new String(decodeResult, sCoding);
            System.out.println(sysout);
            // return sysout;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 释放http连接
        myPost.releaseConnection();
       // return null;
    }
}
