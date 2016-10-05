package patrykpoborca.io.daggerutilitiesdemo;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import patrykpoborca.io.daggerutilitiesdemo.dagger.ActivityModule;
import patrykpoborca.io.daggerutilitiesdemo.dagger.ActivityScope;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by patrykpoborca on 7/9/16.
 */
@ActivityScope
public class BundleDelegate implements BundleListeners.OutBundle {


    private final Observable<Bundle> inBundle;
    private List<BundleListeners.OutBundle> bundleListeners = new ArrayList<>(1);

//    @Inject
//    @Named(AppModule.UI_SCHEDULER)
//    Scheduler uiSCheduler;

    @Inject
    BundleDelegate(@Named(ActivityModule.IN_BUNDLE) Observable<Bundle> inBundle, BaseActivity baseActivity) {
        this.inBundle = inBundle;
        baseActivity.setDelegate(this);
    }

    void onSaveInstance(BundleListeners.InBundle inBundle, BundleListeners.OutBundle outBundle) {
        this.inBundle
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inBundle::onInstanceResumed);
        bundleListeners.add(outBundle);
    }

    @Override
    public void onSaveInstance(Bundle outbundle) {
        for (int i = 0; i < bundleListeners.size(); i++) {
            bundleListeners.get(i).onSaveInstance(outbundle);
        }
    }

}
