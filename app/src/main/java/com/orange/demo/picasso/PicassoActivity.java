package com.orange.demo.picasso;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.orange.demo.R;
import com.orange.demo.base.CommonAdapter;
import com.orange.demo.base.activity.BaseButterKnifeActivity;
import com.orange.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class PicassoActivity extends BaseButterKnifeActivity {
    @Bind(R.id.lv_img)
    protected ListView lvImg;

    private CommonAdapter<String> mAdapter;
    private List<String> mData;

    @Override
    public void onLoadData() {
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947393932&di=803278e79e288109924b4ee5e679889f&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160309%2F9-16030Z92137.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947393932&di=803278e79e288109924b4ee5e679889f&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160309%2F9-16030Z92137.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947393932&di=803278e79e288109924b4ee5e679889f&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947393932&di=5af633eb7fe45459e4b1fa87e2cf567c&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160309%2F9-16030Z92133.jpg%2F160309%2F9-16030Z92137.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947393932&di=803278e79e288109924b4ee5e679889f&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947393932&di=5af633eb7fe45459e4b1fa87e2cf567c&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160309%2F9-16030Z92133.jpg%2F160309%2F9-16030Z92137.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947466390&di=84dd2c1cd2963e42b2f3b4b1b5cf579e&imgtype=jpg&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb13495409230be895659a58d109b3de492c.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947466390&di=84dd2c1cd2963e42b2f3b4b1b5cf579e&imgtype=jpg&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb13495409230be895659a58d109b3de492c.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947490997&di=3fe92a70817039c6982ab0192ef2dc39&imgtype=jpg&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F55e736d12f2eb9382c718be9dd628535e5dd6f89.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947490997&di=3fe92a70817039c6982ab0192ef2dc39&imgtype=jpg&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F55e736d12f2eb9382c718be9dd628535e5dd6f89.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947394227&di=8a9484978da17f61c151c04cf2cdd88f&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160419%2F9-16041Z91K5.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497947394227&di=8a9484978da17f61c151c04cf2cdd88f&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F160419%2F9-16041Z91K5.jpg");
    }

    @Override
    public void onEffectiveClick(View v) {

    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_pacasso;
    }

    @Override
    public void initVars() {
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<String>(this, mData, R.layout.item_activity_pacasso) {
            @Override
            public void convert(ViewHolder holder, String url, int position) {
                LogUtil.log("url: " + url);
//                holder.displyImage(R.id.iv_img,url);
                PicassoUtils.getPicasso(context).with(context).load(url).into((ImageView) holder.getView(R.id.iv_img));
            }
        };
    }

    @Override
    public void bindListenerAdapter() {
        lvImg.setAdapter(mAdapter);
    }

    @Override
    public void onCreateFinish() {

    }
}
