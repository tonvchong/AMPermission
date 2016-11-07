package cc.tonv.android.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by tonvchong on 2016/11/7 0007.
 */
@Target(ElementType.METHOD)
public @interface PermissionDenied {
    int value();
}
