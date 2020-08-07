package com;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: York
 * @Date: 2020/8/4 9:47
 * @Description: testNC
 */
public class testNC {


    public static String Signature(String sContentSign) throws Exception {
        // NC IP地址
        //String NCIp = "10.193.48.105";
        String NCIp = "192.168.0.115";
        // NC 加密端口号
        String NCPort = "7070";
        // 签名端口号
        String NCPort2 = "7080";
        String sCoding = "GBK";
        //接口版本 0.0.0.1或者0.0.1.0
        String cmpVersion = "0.0.1.0";
        //是否大文件方式
        String sZip = "0";
        String sLanguage = "zh_CN";
        //下面字段与明文xml包中保持一致
        //包序列号与xml包中保持一致

        //交易代码
        String sTransCode = "PAYENT";
        //银行编码
        String sBankCode = "102";
        //集团CIS号
        String sGroupCIS = "110290001987990";
        //证书ID
        String sID = "FangYQHL191224.y.1102";

        String repSignContent = null;
        try {
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

            repSignContent = repContent.toString().split("<sign>")[1].split("</sign>")[0];
            return repSignContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String sPackageID = QueryDataUtil.getRandom(15);
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
        String s=Signature(sContentSign);
        if (null==s){
            System.out.println("银行端发生错误");
        }
        System.out.println(s);
    }
}
