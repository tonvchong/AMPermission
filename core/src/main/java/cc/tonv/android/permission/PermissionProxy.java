package cc.tonv.android.permission;

/**
 * Created by tonvchong on 2016/11/7 0007.
 */
public interface PermissionProxy<T> {
    void grant(T source, int requestCode);

    void denied(T source, int requestCode);

    void rationale(T source, int requestCode);

    boolean needShowRationale(int requestCode);
}
