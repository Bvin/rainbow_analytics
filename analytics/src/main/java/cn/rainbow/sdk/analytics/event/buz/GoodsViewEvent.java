package cn.rainbow.sdk.analytics.event.buz;

/**
 * Created by bvin on 2016/6/13.
 * <p>商品浏览事件.
 */
public class GoodsViewEvent extends THPageEvent {

    private String mGoodsId;
    private String mGoodsName;
    private String mGoodsImage;

    private String mId;
    private String mUid;

    private String mCategory1;
    private String mCategory2;
    private String mCategory3;

    public void setGoodsId(String goodsId) {
        mGoodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        mGoodsName = goodsName;
    }

    public void setGoodsImage(String goodsImage) {
        mGoodsImage = goodsImage;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public void setCategory1(String category1) {
        mCategory1 = category1;
    }

    public void setCategory2(String category2) {
        mCategory2 = category2;
    }

    public void setCategory3(String category3) {
        mCategory3 = category3;
    }

    public String getGoodsId() {
        return mGoodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public String getGoodsImage() {
        return mGoodsImage;
    }

    public String getId() {
        return mId;
    }

    public String getUid() {
        return mUid;
    }

    public String getCategory1() {
        return mCategory1;
    }

    public String getCategory2() {
        return mCategory2;
    }

    public String getCategory3() {
        return mCategory3;
    }
}
