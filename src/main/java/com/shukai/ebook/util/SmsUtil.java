package com.shukai.ebook.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsUtil {
    public static String sendSMS(String phone) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIfneAJ9ALCd8y", "C0nDIt60sB6zL5cdLuzJ2uvNpiZI0q");
        IAcsClient client = new DefaultAcsClient(profile);

        String code = KeyUtil.generate(6);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "Sekay");
        request.putQueryParameter("TemplateCode", "SMS_171358264");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");

        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());

        JSONObject json = JSONObject.parseObject(response.getData());
        String Code = json.getString("Code");
        if (Code.equals("OK")) {
            return code;
        } else{
            return "发送失败!";
        }
    }

}
