package routefinder.adapter;

public interface FileIOAdapter {

    void loadDistances(String filename);

    String calculateDistances(String filename);

    void writeToOutput(String filename, String result);

}
