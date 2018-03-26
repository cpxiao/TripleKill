package com.cpxiao.triplekill.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.cpxiao.gamelib.mode.common.Sprite;
import com.cpxiao.gamelib.views.BaseSurfaceViewFPS;
import com.cpxiao.triplekill.mode.MainGame;
import com.cpxiao.triplekill.mode.extra.Difficulty;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author cpxiao on 2017/10/9.
 */

public class GameView extends BaseSurfaceViewFPS {

    private MainGame mMainGame;
    protected ConcurrentLinkedQueue<Sprite> mSpriteQueue = new ConcurrentLinkedQueue<>();

    public GameView(Context context, int countX, int countY) {
        super(context);
        init(countX, countY);
    }

    public GameView(Context context) {
        super(context);
        init(Difficulty._default[Difficulty.INDEX_COUNT_X], Difficulty._default[Difficulty.INDEX_COUNT_Y]);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(Difficulty._default[Difficulty.INDEX_COUNT_X], Difficulty._default[Difficulty.INDEX_COUNT_Y]);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(Difficulty._default[Difficulty.INDEX_COUNT_X], Difficulty._default[Difficulty.INDEX_COUNT_Y]);
    }


    private void init(int countX, int countY) {
        if (countX <= 0 || countY <= 0) {
            if (DEBUG) {
                throw new IllegalArgumentException("countX = " + countX + ", countY = " + countY);
            }
        }
        if (DEBUG) {
            Log.d(TAG, "init: countX = " + countX + ", countY = " + countY);
        }
        mMainGame = new MainGame(countX, countY);
    }

    @Override
    protected void initWidget() {
        setBgColor(Color.GRAY);
        mMainGame.init(mViewWidth, mViewHeight);

    }

    @Override
    public void drawCache() {
        mMainGame.draw(mCanvasCache, mPaint);
        drawSpriteQueue(mCanvasCache, mPaint);
    }

    private void drawSpriteQueue(Canvas canvas, Paint paint) {
        for (Sprite sprite : mSpriteQueue) {
            sprite.draw(canvas, paint);
        }
    }

    @Override
    protected void timingLogic() {
        removeDestroyedSprite();
    }

    private void removeDestroyedSprite() {
        for (Sprite sprite : mSpriteQueue) {
            if (sprite != null && sprite.isDestroyed()) {
                mSpriteQueue.remove(sprite);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float eventX = event.getX();
        float eventY = event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            mMainGame.actionDown(eventX, eventY);
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (mMainGame.isBaseSelected) {
                mMainGame.setBasePosition(eventX, eventY);
            }
        } else if (action == MotionEvent.ACTION_UP) {
            mMainGame.isBaseSelected = false;
            if (mMainGame.isBaseCanBePlaced()) {
                // update blockArray

                // create new base

            } else {
                //put back the base
                mMainGame.putBackBase();
            }
        }
        return true;
    }
}
