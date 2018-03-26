package com.cpxiao.triplekill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cpxiao.R;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;

/**
 * @author cpxiao on 2017/9/27.
 */

public class HomeFragment extends BaseZAdsFragment implements View.OnClickListener {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Button easy = (Button) view.findViewById(R.id.easy);
        Button normal = (Button) view.findViewById(R.id.normal);
        Button hard = (Button) view.findViewById(R.id.hard);
        Button insane = (Button) view.findViewById(R.id.insane);
        Button bestScore = (Button) view.findViewById(R.id.best_score);
        Button settings = (Button) view.findViewById(R.id.settings);
        Button quit = (Button) view.findViewById(R.id.quit);

        easy.setOnClickListener(this);
        normal.setOnClickListener(this);
        hard.setOnClickListener(this);
        insane.setOnClickListener(this);
        bestScore.setOnClickListener(this);
        settings.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Context context = getHoldingActivity();
        if (id == R.id.easy) {

        } else if (id == R.id.normal) {

        } else if (id == R.id.hard) {

        } else if (id == R.id.insane) {

        } else if (id == R.id.best_score) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.quit) {
            removeFragment();
        }
    }
}
