package tak.server.federation.hub.broker;

import java.io.Serializable;

/*
 * An addressable entity is a wrapper that holds information
 * about a remote entity on a network. This entity may be
 * identified by a URI, or something else, like a UID or a callsign.
 */
public class AddressableEntity<T> implements Serializable, Comparable<AddressableEntity<?>>{
    private static final long serialVersionUID = 1124681280265508465L;
    protected final T entity;

    public AddressableEntity(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }

    public String getType() {
        return entity.getClass().getName();
    }

    @Override
    public String toString() {
        return "AddressableEntity{" +
                "entity=" + entity + ", type=" + getType() +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AddressableEntity<?> that = (AddressableEntity<?>) object;

        return entity.equals(that.entity);

    }

    @Override
    public int hashCode() {
        return entity.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(AddressableEntity<?> other) {
        String thisType = this.getType();
        String otherType = other.getType();

        int typeComparison = thisType.compareTo(otherType);

        if (typeComparison != 0) {
            return typeComparison;
        }

        /* Types are the same, entities should have the same class. */
        if (Comparable.class.isAssignableFrom(this.entity.getClass())){
            Comparable<T> thisEntity = (Comparable<T>) entity;
            T oEntity = (T) other.entity;
            return thisEntity.compareTo(oEntity);
        } else {
            return this.entity.toString().compareTo(other.entity.toString());
        }
    }
}