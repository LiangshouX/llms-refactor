package com.liangshou.llmsrefactor.common;

/**
 * @author X-L-S
 */
public class UUIDs {
    private UUIDs() {
    }

    public static String normalize (String uuid) {
        return uuid.replace("-", "");
    }
}
