package org.builovn.storage.parsers.contract;

public class ContractCSVParserException extends Exception {
    public ContractCSVParserException(Throwable cause){
        super(cause);
    }
    public ContractCSVParserException(String message){
        super(message);
    }
}
