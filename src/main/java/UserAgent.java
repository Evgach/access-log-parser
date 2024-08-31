import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {

    private final String operationSystemType;
    private final String browserType;
    private final boolean isbot;

    public UserAgent(LogEntry line) {
        operationSystemType = String.valueOf(extractSubstringsForOS(line));
        browserType = String.valueOf(extractSubstringsForBrowser(line));
        isbot = line.getUserAgent().contains("bot");
    }

    public String getOperationSystemType() {
        return operationSystemType;
    }

    public String getBrowserType() {
        return browserType;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "operationSystemType='" + operationSystemType + '\'' +
                ", browserType='" + browserType + '\'' +
                '}';
    }

    // ћетод дл€ извлечени€ подстроки операционной системы
    public List<String> extractSubstringsForOS(LogEntry line) {
        String input = line.getUserAgent();
        String regex = "Windows|Mac OS|Linux";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();

        if (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches;
    }

    // ћетод дл€ извлечени€ подстроки браузера
    public List<String> extractSubstringsForBrowser(LogEntry line) {
        String input = line.getUserAgent();
        String regex = "Edge|Firefox|Chrome|Opera";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();

        if (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches;
    }

    public boolean getIsbot() {
        return isbot;
    }
}
