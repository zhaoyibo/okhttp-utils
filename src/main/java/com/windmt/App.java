package com.windmt;

import com.windmt.constant.Method;
import com.windmt.util.CurlTransformer;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {

        String curl =
                "curl -k -X GET -H \"User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.0 Mobile/15E148 Safari/604.1\" -H \"Cookie: SE_LAUNCH=5%3A25440831_0%3A25441142; BDSVRBFE=Go; Hm_lpvt_12423ecbc0e2ca965d84259063d35238=1526468537; Hm_lvt_12423ecbc0e2ca965d84259063d35238=1526468537; __bsi=11293785358938720559_00_9_N_N_5_0303_cca8_Y; plus_cv=1::m:caddfa4f; wise_tj_ub=ci%40169_22_169_22_127_186_141; plus_lsv=de8ce127d8872de4; BDSVRTM=35; bd_traffictrace=161902; MSA_PBT=146; MSA_WH=414_622; MSA_ZOOM=1000; wpr=0; H_WISE_SIDS=102569_123405_123292_100099_123511_120140_118878_118875_118841_118819_118801_120549_107313_117327_117433_122791_123573_122959_123700_123500_110085_123290_100460; PSINO=7; wise_tj_cl=i@0|v@1|sInfo@414_736_414_736_414_622|fInfo@0_0_0_0|dpr@3; BDORZ=AE84CDB3A529C0F8A2B9DCDD1D18B695; lsv=wwwTcss_e63f8cf-wwwBcss_4464d60-sugjs_5132c2c-wwwjs_bc3613b-globalTjs_56c8eef-framejs_75ab992-globalBjs_43f6f20; COOKIE_SESSION=q:[{i:\\\"815f891f74ec067c\\\",t:80622},{k:\\\"%E6%AF%9B%E5%8F%91%E6%A0%B9%E9%83%A8%E7%99%BD%E8%89%B2\\\",i:\\\"75acdd2c77ac3d5b\\\",t:30716}],t:\\\"w1\\\",b:0; BIDUPSID=766B41A010877B211CD6CEE16BBEE4B6; BAIDUID=766B41A010877B211CD6CEE16BBEE4B6:FG=1\" -H \"Host: m.baidu.com\" -H \"Referer: https://m.baidu.com/?ssid=0&from=1000539d&uid=0&pu=usm@5,sz@1320_2001,ta@iphone_1_11.3_3_605&bd_page_type=1&baiduid=766B41A010877B211CD6CEE16BBEE4B6&tj=www_sitelink_normal_1_0_10_title\" -H \"DNT: 1\" \"https://m.baidu.com/su?pre=1&p=3&ie=utf-8&json=1&from=wise_web&net=1&os=1&sp=228&callback=jsonp3&wd=github&_=1526468540908\"";

        CurlTransformer transformer = new CurlTransformer(curl);
        Method method = transformer.getMethod();
        String ret = null;

        if (method == Method.GET) {
            ret = OkHttpUtils.get()
                    .url(transformer.getBaseUrl())
                    .queries(
                            transformer.getQueries()
                    )
                    .headers(
                            transformer.getHeaders()
                    )
                    .cookies(
                            transformer.getCookies()
                    )
                    .build()
                    .execute()
                    .body()
                    .string();
        } else if (method == Method.POST) {
            ret = OkHttpUtils.post()
                    .url(transformer.getBaseUrl())
                    .queries(
                            transformer.getQueries()
                    )
                    .headers(
                            transformer.getHeaders()
                    )
                    .cookies(
                            transformer.getCookies()
                    )
                    .params(
                            transformer.getParams()
                    )
                    .build()
                    .execute()
                    .body()
                    .string();
        }

        System.out.println(ret);


    }
}
