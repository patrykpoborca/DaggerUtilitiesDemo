package patrykpoborca.io.daggerutilitiesdemo.dagger;

import dagger.Component;
import dagger.Subcomponent;
import patrykpoborca.io.daggerutilitiesdemo.MainActivity;

/**
 * Created by patrykpoborca on 10/5/16
 */
@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
    FragmentComponent createFragComponent(FragmentModule fragmentModule);

    @Subcomponent(modules = FragmentModule.class)
    interface FragmentComponent {

    }
}
