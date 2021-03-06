package org.builovn.storage.DI;

public class InjectorException extends Exception {
    public InjectorException(Throwable cause){
        super(cause);
    }
    public InjectorException(String message){
        super(message);
    }
}
