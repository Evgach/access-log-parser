import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashSet<String> addressesOfExistingPages;
    private final HashMap<String, Integer> userOS;

    public Statistics() {
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
        addressesOfExistingPages = new HashSet<>();
        userOS = new HashMap<>();
    }

    public void addEntry(LogEntry line) {
        UserAgent agent = new UserAgent(line);
        totalTraffic += line.getResponseSize();
        if (this.minTime.isAfter(line.getTime())) {
            minTime = line.getTime();
        }
        if (this.maxTime.isBefore(line.getTime())) {
            maxTime = line.getTime();
        }
        if (line.getResponseCode() == 200) {
            addressesOfExistingPages.add(line.getPath());
        }
        if (userOS.containsKey("Windows") & agent.getOperationSystemType().contains("Windows")) {
            userOS.put("Windows", userOS.get("Windows") + 1);
        } else {
            userOS.putIfAbsent("Windows", 1);
        }
        if (userOS.containsKey("Mac OS") & agent.getOperationSystemType().contains("Mac OS")) {
            userOS.put("Mac OS", userOS.get("Mac OS") + 1);
        } else {
            userOS.putIfAbsent("Mac OS", 1);
        }
        if (userOS.containsKey("Linux") & agent.getOperationSystemType().contains("Linux")) {
            userOS.put("Linux", userOS.get("Linux") + 1);
        } else {
            userOS.putIfAbsent("Linux", 1);
        }
    }

    public HashMap<String, Double> statisticsOfOS() {
        HashMap<String, Double> fraction = new HashMap<>();
        Set<String> oses = userOS.keySet(); // Сохраняем ключи
        ArrayList<Integer> values = new ArrayList<>(userOS.values()); // Сохраняем значения
        List <String> listOfOses = new ArrayList<>();// Пересохраняем ключи в лист, чтобы ключи использовать в цикле
        listOfOses.addAll(oses); 
        // Считаем общее количество ОС
        double counterOfOS = 0;
        for (int i = 0; i < values.size(); i++) {
            counterOfOS += values.get(i);
        }
        // Считаем доли ОС
        for (int i = 0; i < listOfOses.size(); i++) {
            fraction.put(listOfOses.get(i), Double.valueOf(userOS.get(listOfOses.get(i)))/counterOfOS);
        }

        return fraction;
    }

    public long getTrafficRate() {
        long result = totalTraffic / (minTime.until(maxTime, ChronoUnit.HOURS));
        return result;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public HashSet<String> getAddressesOfExistingPages() {
        return addressesOfExistingPages;
    }

    public HashMap<String, Integer> getUserOS() {
        return userOS;
    }
}
