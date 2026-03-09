package CoreLogic;

class FileResult {

    private String fileName;
    private int lineCount;
    private int wordCount;

    public FileResult(String fileName, int lineCount, int wordCount) {
        this.fileName = fileName;
        this.lineCount = lineCount;
        this.wordCount = wordCount;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getWordCount() {
        return wordCount;
    }
}