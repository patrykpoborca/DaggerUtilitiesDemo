package patrykpoborca.io.daggerutilitiesdemo;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by patrykpoborca on 10/5/16
 */
public class TestInjection {

    @Inject
    TestInjection(BundleDelegate delegate, Context context, LifecycleComposer composer) {
        delegate.onSaveInstance(b -> Toast.makeText(context, b.getString("test"), Toast.LENGTH_SHORT).show(), b -> b.putString("test", "hi"));
        Observable.never()
                .compose(composer.bindUntilEventMainThread(LifecycleComposer.UniversalEvents.DESTROY))
                .doOnTerminate(() -> Toast.makeText(context, "Unsubscribed!", Toast.LENGTH_SHORT).show())
                .subscribe();
    }
}
