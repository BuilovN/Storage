package org.builovn.storage.DI;

/**
 * Исключение, вызываемое при ошибке внедрения зависимости.
 */
public class InjectorException extends Exception {
    public InjectorException(Throwable cause){
        super(cause);
    }
    public InjectorException(String message){
        super(message);
    }
}
