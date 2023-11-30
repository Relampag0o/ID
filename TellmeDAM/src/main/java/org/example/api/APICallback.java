package org.example.api;

import java.io.IOException;
import java.util.Map;

public interface APICallback {
    void onSuccess(Object response) throws IOException;
    void onError(Object error);
}