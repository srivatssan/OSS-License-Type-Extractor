@Configuration
public class OrtEnvironmentConfig {

    @PostConstruct
    public void setupOrt() {
        OrtMain.setLogLevel("warn");
        OrtMain.configureEnvironmentVariables(); // Optional for caching, etc.
    }
}
