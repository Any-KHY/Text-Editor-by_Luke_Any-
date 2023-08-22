import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    public static TextEditorConfig loadConfig(String configFile) throws IOException {
        try (FileReader reader = new FileReader(configFile)) {
            Yaml yaml = new Yaml();
            return yaml.loadAs(reader, TextEditorConfig.class);
        }
    }
}