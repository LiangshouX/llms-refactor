package com.liangshou.llmsrefactor.document.importer.exception;

import java.nio.file.Path;

/**
 * @author X-L-S
 */
public class DocumentImportException extends Exception{
    private final Path path;

    public DocumentImportException(Path path){
        this.path = path;
    }

    public DocumentImportException(Path path, Throwable cause){
        super(cause);
        this.path = path;
    }

    @Override
    public String getMessage() {
        return "Failed to import document %s!".formatted(path);
    }
}
