package cc.tonv.android.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface PermissionGrant {
    int value();
}
