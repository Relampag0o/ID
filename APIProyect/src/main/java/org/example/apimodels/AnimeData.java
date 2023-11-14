package org.example.apimodels; 
import java.util.ArrayList;

public class AnimeData {
    public ArrayList<Datum> data;
    public Meta meta;
    public Links links;

    @Override
    public String toString() {
        return "AnimeData{" +
                "data=" + data +
                ", meta=" + meta +
                ", links=" + links +
                '}';
    }
}
