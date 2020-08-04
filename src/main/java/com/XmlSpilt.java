package com;

import org.junit.Test;

import static com.QueryDataUtil.*;

/**
 * @Author: York
 * @Date: 2020/7/10  11:58
 * @Description: XmlSpilt
 */
public class XmlSpilt {

    @Test
    public void test1() {
        String xml = "<?xml  version=\"1.0\" encoding=\"GB2312\" ?>\n" +
                "<CMS>\n" +
                "<eb>\n" +
                "<pub>\n" +
                "<TransCode>QPAYPER</TransCode>\n" +
                "<CIS>110290001987990</CIS>\n" +
                "<ID>FangYQHL191224.y.1102</ID>\n" +
                "<BankCode>102</BankCode>\n" +
                "<TranDate>20200702</TranDate>\n" +
                "<TranTime>115323989</TranTime>\n" +
                "<fSeqno>20200630121234456</fSeqno>\n" +
                "<RetCode>0</RetCode>\n" +
                "<RetMsg></RetMsg>\n" +
                "</pub>\n" +
                "<out>\n" +
                "<QryfSeqno></QryfSeqno>\n" +
                "<QrySerialNo>CMN750958568</QrySerialNo>\n" +
                "<OnlBatF>1</OnlBatF>\n" +
                "<SettleMode>2</SettleMode>\n" +
                "<BusType></BusType>\n" +
                "<THBaseAccFlag>0</THBaseAccFlag>\n" +
                "<RegSerialNO></RegSerialNO>\n" +
                "<PackageName></PackageName>\n" +
                "<TotalSummary></TotalSummary>\n" +
                "<BatchSumFlag></BatchSumFlag>\n" +
                "<BatRetCode>0</BatRetCode>\n" +
                "<BatRetMsg></BatRetMsg>\n" +
                "<rd>\n" +
                "<iSeqno>1</iSeqno>\n" +
                "<QryiSeqno>111</QryiSeqno>\n" +
                "<QryOrderNo>1</QryOrderNo>\n" +
                "<ReimburseNo></ReimburseNo>\n" +
                "<ReimburseNum></ReimburseNum>\n" +
                "<StartDate></StartDate>\n" +
                "<StartTime></StartTime>\n" +
                "<PayType>1</PayType>\n" +
                "<PayAccNo>1102021929000036344</PayAccNo>\n" +
                "<PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>\n" +
                "<PayAccNameEN></PayAccNameEN>\n" +
                "<RecAccNo>1102022009000767169</RecAccNo>\n" +
                "<RecAccNameCN>强貌坡迄匆末魏输移氯零闸涛建桂迄</RecAccNameCN>\n" +
                "<RecAccNameEN></RecAccNameEN>\n" +
                "<SysIOFlg></SysIOFlg>\n" +
                "<IsSameCity></IsSameCity>\n" +
                "<RecICBCCode></RecICBCCode>\n" +
                "<RecCityName></RecCityName>\n" +
                "<RecBankNo></RecBankNo>\n" +
                "<RecBankName></RecBankName>\n" +
                "<CurrType>001</CurrType>\n" +
                "<PayAmt>1</PayAmt>\n" +
                "<UseCode></UseCode>\n" +
                "<UseCN>工资</UseCN>\n" +
                "<EnSummary></EnSummary>\n" +
                "<PostScript></PostScript>\n" +
                "<Summary></Summary>\n" +
                "<Ref></Ref>\n" +
                "<Oref></Oref>\n" +
                "<ERPSqn></ERPSqn>\n" +
                "<BusCode></BusCode>\n" +
                "<ERPcheckno></ERPcheckno>\n" +
                "<CrvouhType></CrvouhType>\n" +
                "<CrvouhName></CrvouhName>\n" +
                "<CrvouhNo></CrvouhNo>\n" +
                "<Result>0</Result>\n" +
                "<instrRetCode></instrRetCode>\n" +
                "<instrRetMsg></instrRetMsg>\n" +
                "<BankRetTime></BankRetTime>\n" +
                "<MCardNo></MCardNo>\n" +
                "<MCardName></MCardName>\n" +
                "</rd>\n" +
                "<rd>\n" +
                "<iSeqno>2</iSeqno>\n" +
                "<QryiSeqno>1111</QryiSeqno>\n" +
                "<QryOrderNo>2</QryOrderNo>\n" +
                "<ReimburseNo></ReimburseNo>\n" +
                "<ReimburseNum></ReimburseNum>\n" +
                "<StartDate></StartDate>\n" +
                "<StartTime></StartTime>\n" +
                "<PayType>1</PayType>\n" +
                "<PayAccNo>1102021929000036344</PayAccNo>\n" +
                "<PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>\n" +
                "<PayAccNameEN></PayAccNameEN>\n" +
                "<RecAccNo>1102027009913534215</RecAccNo>\n" +
                "<RecAccNameCN>强貌坡迄匆末魏输移氯樱圾婆桂迄</RecAccNameCN>\n" +
                "<RecAccNameEN></RecAccNameEN>\n" +
                "<SysIOFlg></SysIOFlg>\n" +
                "<IsSameCity></IsSameCity>\n" +
                "<RecICBCCode></RecICBCCode>\n" +
                "<RecCityName></RecCityName>\n" +
                "<RecBankNo></RecBankNo>\n" +
                "<RecBankName></RecBankName>\n" +
                "<CurrType>001</CurrType>\n" +
                "<PayAmt>1</PayAmt>\n" +
                "<UseCode></UseCode>\n" +
                "<UseCN>工资</UseCN>\n" +
                "<EnSummary></EnSummary>\n" +
                "<PostScript></PostScript>\n" +
                "<Summary></Summary>\n" +
                "<Ref></Ref>\n" +
                "<Oref></Oref>\n" +
                "<ERPSqn></ERPSqn>\n" +
                "<BusCode></BusCode>\n" +
                "<ERPcheckno></ERPcheckno>\n" +
                "<CrvouhType></CrvouhType>\n" +
                "<CrvouhName></CrvouhName>\n" +
                "<CrvouhNo></CrvouhNo>\n" +
                "<Result>0</Result>\n" +
                "<instrRetCode></instrRetCode>\n" +
                "<instrRetMsg></instrRetMsg>\n" +
                "<BankRetTime></BankRetTime>\n" +
                "<MCardNo></MCardNo>\n" +
                "<MCardName></MCardName>\n" +
                "</rd>\n" +
                "<rd>\n" +
                "<iSeqno>2</iSeqno>\n" +
                "<QryiSeqno>1111</QryiSeqno>\n" +
                "<QryOrderNo>2</QryOrderNo>\n" +
                "<ReimburseNo></ReimburseNo>\n" +
                "<ReimburseNum></ReimburseNum>\n" +
                "<StartDate></StartDate>\n" +
                "<StartTime></StartTime>\n" +
                "<PayType>1</PayType>\n" +
                "<PayAccNo>1102021929000036344</PayAccNo>\n" +
                "<PayAccNameCN>强貌坡迄匆末魏输移氯零闸兔迄</PayAccNameCN>\n" +
                "<PayAccNameEN></PayAccNameEN>\n" +
                "<RecAccNo>1102027009913534215</RecAccNo>\n" +
                "<RecAccNameCN>强貌坡迄匆末魏输移氯樱圾婆桂迄</RecAccNameCN>\n" +
                "<RecAccNameEN></RecAccNameEN>\n" +
                "<SysIOFlg></SysIOFlg>\n" +
                "<IsSameCity></IsSameCity>\n" +
                "<RecICBCCode></RecICBCCode>\n" +
                "<RecCityName></RecCityName>\n" +
                "<RecBankNo></RecBankNo>\n" +
                "<RecBankName></RecBankName>\n" +
                "<CurrType>001</CurrType>\n" +
                "<PayAmt>1</PayAmt>\n" +
                "<UseCode></UseCode>\n" +
                "<UseCN>工资</UseCN>\n" +
                "<EnSummary></EnSummary>\n" +
                "<PostScript></PostScript>\n" +
                "<Summary></Summary>\n" +
                "<Ref></Ref>\n" +
                "<Oref></Oref>\n" +
                "<ERPSqn></ERPSqn>\n" +
                "<BusCode></BusCode>\n" +
                "<ERPcheckno></ERPcheckno>\n" +
                "<CrvouhType></CrvouhType>\n" +
                "<CrvouhName></CrvouhName>\n" +
                "<CrvouhNo></CrvouhNo>\n" +
                "<Result>7</Result>\n" +
                "<instrRetCode></instrRetCode>\n" +
                "<instrRetMsg></instrRetMsg>\n" +
                "<BankRetTime></BankRetTime>\n" +
                "<MCardNo></MCardNo>\n" +
                "<MCardName></MCardName>\n" +
                "</rd>\n" +
                "</out>\n" +
                "</eb>\n" +
                "</CMS>\n";
        String[] split = xml.split("<rd>");

        System.out.println(split);
        int sum = 0;
        for (int i = 1; i < split.length; i++) {
            System.out.println("第" + i + "段数据==" + split[i]);
            System.out.println(getResult(split[i]));
            if ("7".equals(getResult(split[i]))) {
                int payAmt = Integer.parseInt(split[i].split("<PayAmt>")[1].split("</PayAmt>")[0]);
                System.out.println(payAmt);
                sum += payAmt;
            }
        }
        System.out.println(sum);
    }


}
