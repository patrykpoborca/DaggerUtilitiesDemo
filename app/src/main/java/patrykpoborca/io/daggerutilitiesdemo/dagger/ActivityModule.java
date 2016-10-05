package patrykpoborca.io.daggerutilitiesdemo.dagger;

import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle.ActivityEvent;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import patrykpoborca.io.daggerutilitiesdemo.BaseActivity;
import rx.Observable;

/**
 * Created by patrykpoborca on 10/5/16
 */
@Module
public class ActivityModule {

    public static final String IN_BUNDLE = "IN_BUNDLE";
    private final BaseActivity currentActivity;

    public ActivityModule(BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Provides
    @ClassKey(ActivityEvent.class)
    @IntoMap
    @ActivityScope
    public Observable providesLifeCycle() {
        return currentActivity.lifecycle();
    }

    @Provides
    @Named(IN_BUNDLE)
    public Observable<Bundle> providesIn() {
        return this.currentActivity.getInBundle().asObservable();
    }

    @Provides
    public Context providesContext(){
        return this.currentActivity.getApplicationContext();
    }

    @Provides
    BaseActivity providesBaseActivity() {
        return this.currentActivity;
    }

    /*
    Rest of module ommitted
     */
}
