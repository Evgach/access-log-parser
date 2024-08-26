import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
    }

    public void addEntry(LogEntry line) {
        totalTraffic += line.getResponseSize();
        if (this.minTime.isAfter(line.getTime())){
            minTime = line.getTime();
        }
        if (this.maxTime.isBefore(line.getTime())){
            maxTime = line.getTime();
        }
    }

    public long getTrafficRate(){
        long result = totalTraffic/(minTime.until(maxTime, ChronoUnit.HOURS));
        return result;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }
}
