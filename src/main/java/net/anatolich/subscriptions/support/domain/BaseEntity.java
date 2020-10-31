package net.anatolich.subscriptions.support.domain;

import java.util.Objects;

public abstract class BaseEntity<T extends BaseEntity<T, I>, I> {

    static final int HASH = 31;

    public abstract I id();

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final T that = (T) other;
        return id() != null && Objects.equals(id(), that.id());
    }

    @Override
    public int hashCode() {
        return HASH;
    }
}
