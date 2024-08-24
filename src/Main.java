import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int counter = 0; // Счётчик количества файлов
        int totalNumberOfLinesInTheFile = 0; // Счётчик общего количества строк в файле
        int shortestLineInTheFile = 1025; // Длина самой короткой строки в файле
        int longestLineInTheFile = 0; // Длина самой длинной строки в файле

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
                        if (length < shortestLineInTheFile) {
                            shortestLineInTheFile = length;
                        }
                        if (length > longestLineInTheFile) {
                            longestLineInTheFile = length;
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    break;
                }
                System.out.println("Общее количество строк в файле: " + totalNumberOfLinesInTheFile);
                totalNumberOfLinesInTheFile = 0; // Приводим счётчик к исходному значению
                System.out.println("Самая короткая строка в файле: " + shortestLineInTheFile);
                shortestLineInTheFile = 1025; // Приводим счётчик к исходному значению
                System.out.println("Самая длинная строка в файле: " + longestLineInTheFile);
                longestLineInTheFile = 0; // Приводим счётчик к исходному значению
            }
        }
    }
}
