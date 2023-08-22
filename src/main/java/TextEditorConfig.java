public class TextEditorConfig {

    //private String default_font;
    private int defaultFontSize;
    private String defaultFontColour;
    private int tabWidth;
    //private boolean autoSave;

    public int getDefaultFontSize() {
        return defaultFontSize;
    }

    public void setDefaultFontSize(int defaultFontSize) {
        this.defaultFontSize = defaultFontSize;
    }

    public String getDefaultFontColour() {
        return defaultFontColour;
    }

    public void setDefaultFontColour(String defaultFontColour) {
        this.defaultFontColour = defaultFontColour;
    }

    public int getTabWidth() {
        return tabWidth;
    }

    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }
}
