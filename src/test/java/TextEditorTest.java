import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class TextEditorTest {

    @Test
    public void text_default_setting_should_equals_to_config_file() throws IOException {
        // load config file directly from the path and check if it's equals to the setting of text area
        String validConfigFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(validConfigFilePath);
        TextEditor textEditor = new TextEditor(config);
        assertEquals("Expected Default Font Size", config.getDefaultFontSize(),TextEditor.textArea.getTextArea().getFont().getSize() );
        assertEquals("Expected Default Font", config.getDefaultFont(), TextEditor.textArea.getTextArea().getFont().getFontName());
    }

    // Tests for extracting different type of files
    @Test
    public void testFileExtensionCorrectlyExtract() throws IOException {

        // Test txt file extension
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.txt";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("txt", TextEditor.fileExtension);

        // Test CSV file extension
        String csvFileName = "test.csv";
        TextEditor csvEditor = new TextEditor(config);
        csvEditor.extractFileExtension(csvFileName);
        assertEquals("csv", TextEditor.fileExtension);

        // Test Java file extension
        String javaFileName = "test.java";
        TextEditor javaEditor = new TextEditor(config);
        javaEditor.extractFileExtension(javaFileName);
        assertEquals("java", TextEditor.fileExtension);

        // Test C++ file extension
        String cppFileName = "test.cpp";
        TextEditor cppEditor = new TextEditor(config);
        cppEditor.extractFileExtension(cppFileName);
        assertEquals("cpp", TextEditor.fileExtension);

        // Test C file extension
        String cFileName = "test.c";
        TextEditor cEditor = new TextEditor(config);
        cEditor.extractFileExtension(cFileName);
        assertEquals("c", TextEditor.fileExtension);

        // Test Python file extension
        String pythonFileName = "test.py";
        TextEditor pythonEditor = new TextEditor(config);
        pythonEditor.extractFileExtension(pythonFileName);
        assertEquals("py", TextEditor.fileExtension);

        // Test rtf file extension
        String rtfFileName = "test.rtf";
        TextEditor rtfEditor = new TextEditor(config);
        cEditor.extractFileExtension(rtfFileName);
        assertEquals("rtf", TextEditor.fileExtension);

        // Test odt file extension
        String odtFileName = "test.odt";
        TextEditor odtEditor = new TextEditor(config);
        pythonEditor.extractFileExtension(odtFileName);
        assertEquals("odt", TextEditor.fileExtension);

    }


}
