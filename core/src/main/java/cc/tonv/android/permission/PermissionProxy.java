package cc.tonv.android.permission;

public interface PermissionProxy<T> {
    void grant(T source, int requestCode);

    void denied(T source, int requestCode);

    void rationale(T source, int requestCode);

    boolean needShowRationale(int requestCode);
}
