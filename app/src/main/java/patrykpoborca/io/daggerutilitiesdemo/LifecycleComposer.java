package patrykpoborca.io.daggerutilitiesdemo;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by patrykpoborca on 7/9/16.
 */
public class LifecycleComposer {

    private final Observable<ActivityEvent> activityLifeCycle;
    private final Observable<FragmentEvent> fragmentLifeCycle;

    @Inject
    LifecycleComposer(Map<Class<?>, Observable> lifecycles) {
        this.activityLifeCycle = lifecycles.get(ActivityEvent.class);
        this.fragmentLifeCycle = lifecycles.get(FragmentEvent.class);
    }

    @NonNull
    @CheckResult
    private final <TR> LifecycleTransformer<TR> bindToLifecycleActivity() {
        return RxLifecycle.bindActivity(activityLifeCycle);
    }

    //todo does consumer care about state of lifecycle? Eg resumed/paused, recovered from save instance, etc?

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull UniversalEvents event) {
        if(fragmentLifeCycle == null) {
            activityLifeCycle.subscribe(new Action1<ActivityEvent>() {
                @Override
                public void call(ActivityEvent activityEvent) {
                    Timber.d("Lifecycle event %s", activityEvent.toString());
                }
            });
            return RxLifecycle.bindUntilEvent(activityLifeCycle, event.convertToActivityEvent());
        }
        else {
            return RxLifecycle.bindUntilEvent(fragmentLifeCycle, event.convertToFragmentEvent());
        }
    }

    public final <TR> Observable.Transformer<TR, TR> bindUntilEventMainThread(@NonNull final UniversalEvents event) {
        return new Observable.Transformer<TR, TR>() {
            @Override
            public Observable<TR> call(Observable<TR> trObservable) {
                return (Observable<TR>) trObservable.compose(bindUntilEvent(event)).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @NonNull
    @CheckResult
    private final <TR> LifecycleTransformer<TR> bindToLifecycleFragment() {
        return RxLifecycle.bindFragment(fragmentLifeCycle);
    }

    public final <TR> LifecycleTransformer<TR> bindToLifeCycle() {
        return fragmentLifeCycle == null ? this.<TR>bindToLifecycleActivity() : this.<TR>bindToLifecycleFragment();
    }

    public final <TR> Observable.Transformer<TR, TR> bindToLifeCycleMainThread() {
        return new Observable.Transformer<TR, TR>() {
            @Override
            public Observable<TR> call(Observable<TR> trObservable) {
                return (Observable<TR>) trObservable.compose(bindToLifeCycle()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static enum UniversalEvents {
        RESUME,
        PAUSE,
        DESTROY;
        //todo add more mappings
        public ActivityEvent convertToActivityEvent() {
            switch(this) {
                case RESUME:
                    return ActivityEvent.RESUME;
                case PAUSE:
                    return ActivityEvent.PAUSE;
                default:
                case DESTROY:
                    return ActivityEvent.DESTROY;
            }
        }

        public FragmentEvent convertToFragmentEvent() {
            switch(this) {
                case RESUME:
                    return FragmentEvent.RESUME;
                case PAUSE:
                    return FragmentEvent.PAUSE;
                default:
                case DESTROY:
                    return FragmentEvent.DESTROY;
            }
        }
    }
}
