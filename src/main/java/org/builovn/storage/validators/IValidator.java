package org.builovn.storage.validators;

public interface IValidator<T> {
    public Message validate(T object);
}
