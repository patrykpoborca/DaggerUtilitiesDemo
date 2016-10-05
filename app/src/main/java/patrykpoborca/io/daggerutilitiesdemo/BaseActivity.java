package patrykpoborca.io.daggerutilitiesdemo;

import android.os.Bundle;

import com.jakewharton.rxrelay.BehaviorRelay;
import com.trello.rxlifecycle.components.RxActivity;

/**
 * Created by patrykpoborca on 10/5/16
 */
public class BaseActivity extends RxActivity {

    //Normally some level of hiding would be used here of type/function
    BehaviorRelay<Bundle> inBundle = BehaviorRelay.create();
    BundleListeners.OutBundle delegate = null;

    public void setDelegate(BundleListeners.OutBundle delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            inBundle.call(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        delegate.onSaveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    public BehaviorRelay<Bundle> getInBundle() {
        return inBundle;
    }
}
