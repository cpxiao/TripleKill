package com.cpxiao.triplekill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cpxiao.R;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;

/**
 * @author cpxiao on 2017/9/27.
 */

public class GameFragment extends BaseZAdsFragment {

    public static GameFragment newInstance(Bundle bundle) {
        GameFragment fragment = new GameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Context context = getHoldingActivity();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }
}
