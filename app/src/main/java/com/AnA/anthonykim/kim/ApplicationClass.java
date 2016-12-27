package com.AnA.anthonykim.kim;

import android.app.Application;
import android.content.res.Configuration;

import com.tsengvn.typekit.Typekit;

/**
 * Created by anthony on 2016. 12. 5..
 */

public class ApplicationClass extends Application {
    /** onCreate()
     * 액티비티, 리시버, 서비스가 생성되기전 어플리케이션이 시작 중일때
     * Application onCreate() 메서드가 만들어 진다고 나와 있습니다.
     * by. Developer 사이트
     */
    @Override
    public void onCreate() {
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "fonts/SeoulNamsanLongL.ttf"))
                .add("text", Typekit.createFromAsset(this, "fonts/SeoulNamsanLongB.ttf"))
                .add("button", Typekit.createFromAsset(this, "fonts/SeoulNamsanLongEB.ttf"));
        super.onCreate();
    }

    /**
     * onConfigurationChanged()
     * 컴포넌트가 실행되는 동안 단말의 화면이 바뀌면 시스템이 실행 한다.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
