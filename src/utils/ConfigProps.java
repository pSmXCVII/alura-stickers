package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProps {
  private Properties props = new Properties();
  
  private void loadProps () {
    try {
      FileInputStream file = new FileInputStream("config.properties");
      props.load(file);
      file.close();
    } catch (IOException e) {
        System.out.println("Não foi possível carregar o arquivo de props.");
        return;
    }

  }
  
  public String getPropValue(String prop) {
    loadProps ();
    return props.getProperty("api." + prop);
  }
}
