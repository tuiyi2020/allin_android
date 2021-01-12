package com.tuiyi.allin.third.jsdk;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.insertad.CustomInsertAd;

import org.json.JSONException;
import org.json.JSONObject;

import cn.imeiadx.jsdk.jy.mob.JyAd;
import cn.imeiadx.jsdk.jy.mob.JyAdListener2;
import cn.imeiadx.jsdk.jy.mob.JyInsertAd;
import cn.imeiadx.jsdk.jy.mob.JyNative;

/**
 * 推易插屏
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public class JsdkInsertAd extends CustomInsertAd {
    private JyInsertAd mJyInsertAd;
    private JyAdListener2 mJyAdListener2;
    //private String json = "{\"pm\":[\"http:\\/\\/s.adxvip.com\\/showsuc?pid=GL2TTLZJK3JTFWXECFJ1&bid=V4giUhCGdg&sid=JBYBTESCJDYBWTNXNFHC&cid=HYRNJLKVQ9WDCVDMMT4T&size=5010&cat=207&type=N&uk=9f95267d54ff9fdb&ip=111.201.247.41&ua=Mozilla%2F5.0%20%28Linux%3B%20Android%2010%3B%20Mi%2010%20Pro%20Build%2FQKQ1.191117.002%3B%20wv%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Version%2F4.0%20Chrome%2F80.0.3987.99%20Mobile%20Safari%2F537.36&appid=com.example.jsdkdemo&deal=&jytm=1610438779590&ms=640x960&ds=640x960&islongt=N&repkg=&jyaccttype=M&jydtp=R&jyifa=&jyand=3cef4f69a9d717b6&jymc=BA%3ACB%3A45%3ABF%3A15%3AB5&jyime=&jyod=9f95267d54ff9fdb&mtm5k=7474c9379c4fa183aa43a7e1baf79aee&ts=1610438779590&isifr=JYNO3IFR&jygd=F&jymgd=&jyos=3&jynet=1&jyage=6&rid=0.446716&p=ulHX1_WBQd4\"],\"deeplink\":\"tbopen:\\/\\/m.taobao.com\\/tbopen\\/index.html?action=ali.open.nav&module=h5&bc_fl_src=tunion_jiatou_jiatou&h5Url=http%3A%2F%2Fredirect.simba.taobao.com%2Frd%3Fc%3Dun%26w%3Dbd%26k%3D2bd433baf3ad1847%26p%3Dmm_1063180191_1483850248_110212250464%26b%3DU9zI2pcSJ97nmsiP0Hz%26s%3D%26f%3Dhttps%253A%252F%252Fai.m.taobao.com%252F%253Fpid%253Dmm_1063180191_1483850248_110212250464\",\"landingpage_url\":\"http:\\/\\/s.adxvip.com\\/exclick?ad=0&type=p3l&pid=GL2TTLZJK3JTFWXECFJ1&link=http%3A%2F%2Fredirect.simba.taobao.com%2Frd%3Fc%3Dun%26w%3Dbd%26k%3D2bd433baf3ad1847%26p%3Dmm_1063180191_1483850248_110212250464%26b%3DU9zI2pcSJ97nmsiP0Hz%26s%3D%26f%3Dhttps%253A%252F%252Fai.m.taobao.com%252F%253Fpid%253Dmm_1063180191_1483850248_110212250464%26jy_pid%3DGL2TTLZJK3JTFWXECFJ1%26jy_unitid%3DJBYBTESCJDYBWTNXNFHC%26jy_cid%3DHYRNJLKVQ9WDCVDMMT4T%26jy_idfa%3D%26jy_androidid%3D3cef4f69a9d717b6%26jy_oaid%3D9f95267d54ff9fdb%26jy_imei%3D%26jy_mac%3DBA%253ACB%253A45%253ABF%253A15%253AB5%26jy_bid%3D18191fe780fb05b0f1dd4079d7d3d991%26jy_repkg%3D%26jy_gd%3DF%26jy_mgd%3D%26jy_os%3D3%26jy_net%3D1%26jy_age%3D6%26jy_kid%3D9f95267d54ff9fdb\",\"cm\":[\"http:\\/\\/s.adxvip.com\\/cNzExQ0ZBMDMxQjc2OUVC?cliclick=n&pid=GL2TTLZJK3JTFWXECFJ1&bid=V4giUhCGdg&cat=207&uk=9f95267d54ff9fdb&cid=HYRNJLKVQ9WDCVDMMT4T&sid=JBYBTESCJDYBWTNXNFHC&appid=com.example.jsdkdemo&jytm=1610438779590&ms=640x960&ds=640x960&islongt=N&repkg=&jyifa=&jyand=3cef4f69a9d717b6&jymc=BA%3ACB%3A45%3ABF%3A15%3AB5&jyime=&jyod=9f95267d54ff9fdb&mtm5k=7474c9379c4fa183aa43a7e1baf79aee&size=5010&jygd=F&jymgd=&jyos=3&jynet=1&jyage=6&isdcl=y&dinfo=tbopen&ls=0&\"],\"dpcm\":[\"http:\\/\\/s.adxvip.com\\/exclick?ad=0&mode=dp&type=jyssp&pid=GL2TTLZJK3JTFWXECFJ1&bid=V4giUhCGdg&cat=207&sid=JBYBTESCJDYBWTNXNFHC&cid=HYRNJLKVQ9WDCVDMMT4T&uk=9f95267d54ff9fdb&deal=&s2dpid=&parpid=&appid=com.example.jsdkdemo&jytm=1610438779590&islongt=N&ms=640x960&ds=640x960&repkg=&jyifa=&jyand=3cef4f69a9d717b6&jymc=BA%3ACB%3A45%3ABF%3A15%3AB5&jyime=&jyod=9f95267d54ff9fdb&mtm5k=7474c9379c4fa183aa43a7e1baf79aee&size=5010&ls=1610438779590&jygd=F&jymgd=&jyos=3&jynet=1&jyage=6&dinfo=tbopen&\"],\"adurl\":\"http:\\/\\/cdn.51tuiyi.com\\/cdn\\/d2a37f1074afb1d44a4bc7e3d75690e31593773433365.jpg\",\"creative_id\":\"HYRNJLKVQ9WDCVDMMT4T\",\"title\":\"爱淘宝\",\"seat\":\"捷云2\",\"body\":\"爱淘宝--精选好物-狂暑季--便宜好货\",\"app_icon\":\"http:\\/\\/cdn.51tuiyi.com\\/cdn\\/cc35d4b8e796c869289176c383aa3da31525319647825.png\",\"star\":0,\"adveristiser\":\"JADX\",\"logoname\":\"JADX\",\"w\":640,\"h\":960,\"error\":\"\"}";

    public JsdkInsertAd() {

    }

    @Override
    public void loadAd() {
        if (mAdConfig.json == null) {
            notifyAdFail(new AdError(AdErrorCode.NO_AD_ERROR, "无广告"));
            return;
        }
        mJyAdListener2 = new JyAdListener2() {
            @Override
            public void onADClicked() {
                super.onADClicked();
                notifyAdClick();
            }

            @Override
            public void onADExposure() {
                super.onADExposure();
                // notifyAdReady();
            }

            @Override
            public void onADReceive(JyNative jyNative) {
                super.onADReceive(jyNative);

            }

            @Override
            public void onNoAD(String err) {
                super.onNoAD(err);
                notifyAdFail(new AdError(0, err));
            }

            @Override
            public void onClosed() {
                super.onClosed();
                notifyAdClose();
            }

            @Override
            public void onADReceive() {
                super.onADReceive();
                notifyAdReady();
            }
        };
        mJyInsertAd = JyAd.getInsertAd(mActivity, null, mAdConfig.width, mAdConfig.height, mJyAdListener2);
        //mJySplashAd = new JySplashAd(mActivity, mAdConfig.thirdPid, -1, -1, mAdConfig.showTime, mJyAdListener2);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mAdConfig.json);
        } catch (JSONException e) {
            e.printStackTrace();
            notifyAdFail(new AdError(AdErrorCode.PARSE_ERROR, "解析失败"));
            return;
        }
        mJyInsertAd.loadAd(jsonObject);
    }

    @Override
    public void showAd() {

        if (mJyInsertAd == null || !mJyInsertAd.hasAd()) {
            return;
        }
        mJyInsertAd.showAd();
    }

    @Override
    public void destroyAd() {
       // JyAd.exit();
    }

    @Override
    public void onPauseAd() {

    }

    @Override
    public void onResumeAd() {

    }

    @Override
    public void customAdMessage(AdEntity adEntity) {

    }
}
