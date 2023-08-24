import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TextEditorTest {

    @Test
    void text_default_setting_should_equals_to_config_file() throws IOException {
        // load config file directly from the path and check if it's equals to the setting of text area
        String validConfigFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(validConfigFilePath);
        TextEditor textEditor = new TextEditor(config);
        assertEquals("Expected Default Font Size", config.getDefaultFontSize(),TextEditor.textArea.getTextArea().getFont().getSize() );
        assertEquals("Expected Default Font", config.getDefaultFont(), TextEditor.textArea.getTextArea().getFont().getFontName());
    }


}