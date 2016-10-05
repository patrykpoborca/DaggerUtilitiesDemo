package patrykpoborca.io.daggerutilitiesdemo.dagger;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.RxFragment;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import rx.Observable;

/**
 * Created by patrykpoborca on 10/5/16
 */
@Module
public class FragmentModule {

    private final RxFragment baseFragment;

    public FragmentModule(RxFragment rxFragment) {
        this.baseFragment = rxFragment;
    }

    @Provides
    @ClassKey(FragmentEvent.class)
    @IntoMap
    @FragmentScope
    public Observable providesLifeCycle() {
        return baseFragment.lifecycle();
    }
}
