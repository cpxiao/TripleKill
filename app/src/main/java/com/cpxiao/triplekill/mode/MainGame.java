package com.cpxiao.triplekill.mode;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.cpxiao.AppConfig;
import com.cpxiao.gamelib.mode.common.SpriteControl;

/**
 * @author cpxiao on 2017/11/09.
 */

public class MainGame {
    private static final boolean DEBUG = AppConfig.DEBUG;
    private final String TAG = getClass().getSimpleName();

    private int mCountX;
    private int mCountY;

    private Holder mScoreView;
    private Holder mBestScoreView;
    private Holder[][] mBlockArray;

    private Holder mBaseHolder;
    //    private Holder mBase0Holder, mBase1Holder, mBase2Holder;
    private Holder mBase;

    public boolean isBaseSelected = false;

    public MainGame(int countX, int countY) {
        mCountX = countX;
        mCountY = countY;
        mBlockArray = new Holder[countY][countX];
    }


    public void init(int viewWidth, int viewHeight) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        float titleBarH = 100 * density;
        float baseH = 80 * density;
        float adsLayoutH = 100 * density;
        float gameW = 0.8F * viewWidth;
        float gameH = viewHeight - titleBarH - baseH - adsLayoutH;

        // init scoreView, bestScoreView
        mScoreView = (Holder) new Holder.Build()
                .setW(0.3F * viewWidth)
                .setH(0.5F * titleBarH)
                .centerTo(0.5F * viewWidth, 0.25F * titleBarH)
                .build();

        mBestScoreView = (Holder) new Holder.Build()
                .setW(0.3F * viewWidth)
                .setH(0.5F * titleBarH)
                .centerTo(0.5F * viewWidth, 0.75F * titleBarH)
                .build();

        // init block array
        float blockWH = Math.min(gameW / mCountX, gameH / mCountY);
        float blockPaddingL = 0.5F * (viewWidth - blockWH * mCountX);
        float blockPaddingT = titleBarH + 0.5F * (gameH - blockWH * mCountY);
        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                Holder block = (Holder) new Holder.Build()
                        .setW(0.9F * blockWH)
                        .setH(0.9F * blockWH)
                        .centerTo(blockPaddingL + (0.5F + x) * blockWH, blockPaddingT + (0.5F + y) * blockWH)
                        .build();
                mBlockArray[y][x] = block;
            }
        }

        //init base
        float baseWH = Math.min(0.8F * viewWidth / 4, 0.9F * baseH);
        //        float basePaddingL = 0.5F * (viewWidth - baseWH * 4);
        float basePaddingL = 0.5F * (viewWidth - baseWH * 1);
        float basePaddingT = titleBarH + gameH + 0.5F * (baseH - baseWH);
        mBaseHolder = (Holder) new Holder.Build()
                .setW(baseWH)
                .setH(baseWH)
                .centerTo(basePaddingL + (0.5F + 0) * baseWH, basePaddingT + (0.5F) * baseWH)
                .build();
        //        mBase0Holder = (Holder) new Holder.Build()
        //                .setW(baseWH)
        //                .setH(baseWH)
        //                .centerTo(basePaddingL + (0.5F + 1) * baseWH, basePaddingT + (0.5F) * baseWH)
        //                .build();
        //        mBase1Holder = (Holder) new Holder.Build()
        //                .setW(baseWH)
        //                .setH(baseWH)
        //                .centerTo(basePaddingL + (0.5F + 2) * baseWH, basePaddingT + (0.5F) * baseWH)
        //                .build();
        //        mBase2Holder = (Holder) new Holder.Build()
        //                .setW(baseWH)
        //                .setH(baseWH)
        //                .centerTo(basePaddingL + (0.5F + 3) * baseWH, basePaddingT + (0.5F) * baseWH)
        //                .build();

        randomBase();
    }

    private void randomBase() {
        mBase = (Holder) new Holder.Build()
                .setW(mBaseHolder.getWidth())
                .setH(mBaseHolder.getHeight())
                .centerTo(mBaseHolder.getCenterX(), mBaseHolder.getCenterY())
                .build();
    }

    /**
     * pa判断是否选中待选块
     */
    public void actionDown(float eventX, float eventY) {
        actionDownBase(eventX, eventY);
    }

    /**
     * pa判断是否选中待选块
     */
    private void actionDownBase(float eventX, float eventY) {
        isBaseSelected = SpriteControl.isClicked(mBase, eventX, eventY);
    }

    public void setBasePosition(float eventX, float eventY) {
        mBase.centerTo(eventX, eventY);
    }

    public boolean isBaseCanBePlaced() {
        boolean isBaseCanBePlaced = false;
        int indexX = -1;
        int indexY = -1;
        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                if (SpriteControl.isClicked(mBlockArray[y][x], mBase.getCenterX(), mBase.getCenterY())) {
                    indexX = x;
                    indexY = y;
                    isBaseCanBePlaced = true;
                }
            }
        }
        if (DEBUG) {
            Log.d(TAG, "isBaseCanBePlaced: indexX = " + indexX + ", indexY = " + indexY);
        }

        return isBaseCanBePlaced;
    }

    public void draw(Canvas canvasCache, Paint paint) {
        mScoreView.draw(canvasCache, paint);
        mBestScoreView.draw(canvasCache, paint);

        for (int y = 0; y < mCountY; y++) {
            for (int x = 0; x < mCountX; x++) {
                mBlockArray[y][x].draw(canvasCache, paint);
            }
        }

        mBaseHolder.draw(canvasCache, paint);
        //        mBase0Holder.draw(canvasCache, paint);
        //        mBase1Holder.draw(canvasCache, paint);
        //        mBase2Holder.draw(canvasCache, paint);

        mBase.draw(canvasCache, paint);


    }

    public void putBackBase() {
        mBase.centerTo(mBaseHolder.getCenterX(), mBaseHolder.getCenterY());
    }

    /**
     * 检查升级级数
     *
     * @param store
     * @param indexX
     * @param indexY
     */
    private int checkUpgradeCount(int[][] store, int indexX, int indexY) {
        if (store == null || indexX < 0 || indexY < 0) {
            return 0;
        }
        return 0;
    }
}
