
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.Assert.*;


public class TextEditorTest {


//    @Test
//    void text_default_setting_should_equals_to_config_file() throws IOException {
//        // load config file directly from the path and check if it's equals to the setting of text area
//        String validConfigFilePath = "src/main/resources/config.yaml";
//        TextEditorConfig config = ConfigLoader.loadConfig(validConfigFilePath);
//        TextEditor textEditor = new TextEditor(config);
//        assertEquals("Expected Default Font Size", config.getDefaultFontSize(),TextEditor.textArea.getTextArea().getFont().getSize() );
//        assertEquals("Expected Default Font", config.getDefaultFont(), TextEditor.textArea.getTextArea().getFont().getFontName());
//    }

    // Tests for extracting different type of files

    @Test
    void txt_file_extension_extraction_should_be_txt() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.txt";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("txt", TextEditor.fileExtension);
    }

    @Test
    void csv_file_extension_extraction_should_be_csv() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.csv";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("csv", TextEditor.fileExtension);
    }

    @Test
    void java_file_extension_extraction_should_be_java() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.java";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("java", TextEditor.fileExtension);
    }

    @Test
    void cplusplus_file_extension_extraction_should_be_cpp() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.cpp";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("cpp", TextEditor.fileExtension);
    }


    @Test
    void c_file_extension_extraction_should_be_c() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.c";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("c", TextEditor.fileExtension);
    }
    @Test
    void python_file_extension_extraction_should_be_py() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.py";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("py", TextEditor.fileExtension);
    }
    @Test
    void php_file_extension_extraction_should_be_php() throws IOException {
        String configFilePath = "src/main/resources/config.yaml";
        TextEditorConfig config = ConfigLoader.loadConfig(configFilePath);
        String fileName = "test.php";
        TextEditor textEditor = new TextEditor(config);
        textEditor.extractFileExtension(fileName);
        assertEquals("php", TextEditor.fileExtension);
    }
}