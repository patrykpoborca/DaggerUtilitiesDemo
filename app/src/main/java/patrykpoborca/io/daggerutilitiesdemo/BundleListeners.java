package patrykpoborca.io.daggerutilitiesdemo;

import android.os.Bundle;

/**
 * Created by patrykpoborca on 10/5/16
 */
public class BundleListeners {
    public interface InBundle {
        void onInstanceResumed(Bundle bundle);
    }

    public interface OutBundle {
        void onSaveInstance(Bundle outbundle);
    }
}
