package cn.tuyucheng.taketoday.springevents.synchronous;

public class GenericSpringEvent<T> {
    protected final boolean success;
    private final T what;

    public GenericSpringEvent(final T what, final boolean success) {
        this.what = what;
        this.success = success;
    }

    public T getWhat() {
        return what;
    }

    public boolean isSuccess() {
        return success;
    }
}