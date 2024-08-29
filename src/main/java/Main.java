import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int counter = 0; // Счётчик количества файлов
        int totalNumberOfLinesInTheFile = 0; // Счётчик общего количества строк в файле
        int totalNumberOfYandexBot = 0; // Счётчик количества строк от бота YandexBot
        int totalNumberOfGooglebot = 0; // Счётчик количества строк от бота Googlebot

        // Для печати статистики
        Statistics stat = new Statistics();

        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExists || isDirectory)
                System.out.println("Указанный файл не существует или указанный путь является путём к папке");
            else {
                counter += 1;
                System.out.println("Путь указан верно");
                System.out.println("Это файл номер " + counter);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        totalNumberOfLinesInTheFile += 1;
                        if (length > 1024) {
                            throw new LengthException("Длина строки №" + totalNumberOfLinesInTheFile + " больше 1024 символов");
                        }
                        String[] parts = line.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].strip();
                            String[] bots = fragment.split("/");
                            if (bots.length >= 1) {
                                String botsFragment = bots[0].strip();
                                if (botsFragment.equals("YandexBot")) {
                                    totalNumberOfYandexBot += 1;
                                }
                                if (botsFragment.equals("Googlebot")) {
                                    totalNumberOfGooglebot += 1;
                                }
                            }
                        }
                        // Для печати статистики
                        LogEntry log = new LogEntry(line);
                        stat.addEntry(log);
                        //System.out.println(log.getUserAgent());
                        //Для печати LogEntry
                        //System.out.println(log);
                        // Для печати User Agent
                        UserAgent agent = new UserAgent(log);
                        //System.out.println(agent);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    break;
                }

                double shareOfRequestsOfYandexBot = (double) totalNumberOfYandexBot * 100 / (double) totalNumberOfLinesInTheFile; // Доля запросов от YandexBot
                double shareOfRequestsOfGooglebot = (double) totalNumberOfGooglebot * 100 / (double) totalNumberOfLinesInTheFile; // Доля запросов от Googlebot

                System.out.println("Общее количество строк в файле: " + totalNumberOfLinesInTheFile);
                System.out.println("Доля запросов от YandexBot: " + shareOfRequestsOfYandexBot + ", относительно общего числа сделанных запросов: " + totalNumberOfLinesInTheFile);
                System.out.println("Доля запросов от Googlebot: " + shareOfRequestsOfGooglebot + ", относительно общего числа сделанных запросов: " + totalNumberOfLinesInTheFile);
                totalNumberOfLinesInTheFile = 0; // Приводим счётчик к исходному значению
                totalNumberOfYandexBot = 0; // Приводим счётчик к исходному значению
                totalNumberOfGooglebot = 0; // Приводим счётчик к исходному значению


                System.out.println("Пиковая посещаемость сайта живым человеком в секунду: " + stat.peakWebsiteTrafficByHuman());
                System.out.println("Список доменных имён: " + stat.getDomenNames());
                System.out.println("Максимальное колчиество посещений одним пользователем: " + stat.maxAttendanceByOneUser());
            }
        }
    }
}
