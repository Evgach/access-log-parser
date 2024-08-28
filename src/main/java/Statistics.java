import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashSet<String> addressesOfExistingPages;
    private final HashSet<String> addressesOfNotExistingPages;
    private final HashMap<String, Integer> userOS;
    private final HashMap<String, Integer> userBrowser;
    private long numberSiteVisitors;
    private long numberOfErrorResponseCode;
    private final HashSet<String> uniqueIPs;

    public Statistics() {
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
        addressesOfExistingPages = new HashSet<>();
        addressesOfNotExistingPages = new HashSet<>();
        userOS = new HashMap<>();
        userBrowser = new HashMap<>();
        uniqueIPs = new HashSet<>();
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
        if (line.getResponseCode() == 404) {
            addressesOfNotExistingPages.add(line.getPath());
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
        if (userBrowser.containsKey("Edge") & agent.getBrowserType().contains("Edge")) {
            userBrowser.put("Edge", userBrowser.get("Edge") + 1);
        } else {
            userBrowser.putIfAbsent("Edge", 1);
        }
        if (userBrowser.containsKey("Firefox") & agent.getBrowserType().contains("Firefox")) {
            userBrowser.put("Firefox", userBrowser.get("Firefox") + 1);
        } else {
            userBrowser.putIfAbsent("Firefox", 1);
        }
        if (userBrowser.containsKey("Chrome") & agent.getBrowserType().contains("Chrome")) {
            userBrowser.put("Chrome", userBrowser.get("Chrome") + 1);
        } else {
            userBrowser.putIfAbsent("Chrome", 1);
        }
        if (userBrowser.containsKey("Opera") & agent.getBrowserType().contains("Opera")) {
            userBrowser.put("Opera", userBrowser.get("Opera") + 1);
        } else {
            userBrowser.putIfAbsent("Opera", 1);
        }
        if (line.getUserAgent().contains("bot")) {
            ;
        } else {
            numberSiteVisitors += 1;
        }
        if (line.getResponseCode() >= 400 && line.getResponseCode() < 600){
            numberOfErrorResponseCode += 1;
        }
        if (line.getUserAgent().contains("bot")){
            ;
        } else {
            uniqueIPs.add(line.getIpAddr());
        }
    }

    public HashMap<String, Double> statisticsOfOS() {
        HashMap<String, Double> fraction = new HashMap<>();
        Set<String> oses = userOS.keySet(); // Сохраняем ключи
        ArrayList<Integer> values = new ArrayList<>(userOS.values()); // Сохраняем значения
        List<String> listOfOses = new ArrayList<>();// Пересохраняем ключи в лист, чтобы ключи использовать в цикле
        listOfOses.addAll(oses);
        // Считаем общее количество ОС
        double counterOfOS = 0;
        for (int i = 0; i < values.size(); i++) {
            counterOfOS += values.get(i);
        }
        // Считаем доли ОС
        for (int i = 0; i < listOfOses.size(); i++) {
            fraction.put(listOfOses.get(i), Double.valueOf(userOS.get(listOfOses.get(i))) / counterOfOS);
        }
        return fraction;
    }

    public HashMap<String, Double> statisticsOfBrowsers() {
        HashMap<String, Double> fraction = new HashMap<>();
        Set<String> browsers = userBrowser.keySet(); // Сохраняем ключи
        ArrayList<Integer> values = new ArrayList<>(userBrowser.values()); // Сохраняем значения
        List<String> listOfBrowsers = new ArrayList<>(); // Пересохраняем ключи в лист, чтобы ключи использовать в цикле
        listOfBrowsers.addAll(browsers);
        // Считаем общее количество браузеров
        double counterOfBrowsers = 0;
        for (int i = 0; i < values.size(); i++) {
            counterOfBrowsers += values.get(i);
        }
        // Считаем доли браузеров
        for (int i = 0; i < listOfBrowsers.size(); i++) {
            fraction.put(listOfBrowsers.get(i), Double.valueOf(userBrowser.get(listOfBrowsers.get(i))) / counterOfBrowsers);
        }
        return fraction;
    }

    public long averageNumberOfSiteVisitsPerHour() {
        long result = numberSiteVisitors / (minTime.until(maxTime, ChronoUnit.HOURS));
        return result;
    }

    public long averageNumberOfErrorResponseRequests(){
        long result = numberOfErrorResponseCode / (minTime.until(maxTime, ChronoUnit.HOURS));
        return result;
    }

    public long averageNumberOfOneUserVisits(){
        long result = numberSiteVisitors/uniqueIPs.size();
        return result;
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

    public HashSet<String> getAddressesOfNotExistingPages() {
        return addressesOfNotExistingPages;
    }

    public HashMap<String, Integer> getUserOS() {
        return userOS;
    }

    public HashMap<String, Integer> getUserBrowser() {
        return userBrowser;
    }

    public long getNumberSiteVisitors() {
        return numberSiteVisitors;
    }

    public long getNumberOfErrorResponseCode() {
        return numberOfErrorResponseCode;
    }

    public HashSet<String> getUniqueIPs() {
        return uniqueIPs;
    }
}
