package patrykpoborca.io.daggerutilitiesdemo;

import android.os.Bundle;

import javax.inject.Inject;

import patrykpoborca.io.daggerutilitiesdemo.dagger.ActivityModule;
import patrykpoborca.io.daggerutilitiesdemo.dagger.DaggerActivityComponent;

public class MainActivity extends BaseActivity {


    @Inject TestInjection testInjection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
