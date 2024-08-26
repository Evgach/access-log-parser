import org.openqa.selenium.remote.http.HttpMethod;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {

    private final String ipAddr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final String userAgent;

    public LogEntry(String line) {
        String[] args = line.split("\\s");
        String[] userAgentPart = line.split("\"\\s\"");
        ipAddr = args[0];
        time = LocalDateTime.parse(args[3].replace("[", ""), DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH));
        method = HttpMethod.valueOf(args[5].replace("\"", ""));
        path = args[6];
        responseCode = Integer.parseInt(args[8]);
        responseSize = Integer.parseInt(args[9]);
        referer = args[10];
        userAgent = userAgentPart[1];
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddr='" + ipAddr + '\'' +
                ", time=" + time +
                ", method=" + method +
                ", path='" + path + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
