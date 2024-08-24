import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int counter = 0; // ������� ���������� ������
        int totalNumberOfLinesInTheFile = 0; // ������� ������ ���������� ����� � �����
        int shortestLineInTheFile = 1025; // ����� ����� �������� ������ � �����
        int longestLineInTheFile = 0; // ����� ����� ������� ������ � �����

        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExists || isDirectory)
                System.out.println("��������� ���� �� ���������� ��� ��������� ���� �������� ���� � �����");
            else {
                counter += 1;
                System.out.println("���� ������ �����");
                System.out.println("��� ���� ����� " + counter);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        totalNumberOfLinesInTheFile += 1;
                        if (length > 1024) {
                            throw new LengthException("����� ������ �" + totalNumberOfLinesInTheFile + " ������ 1024 ��������");
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
                System.out.println("����� ���������� ����� � �����: " + totalNumberOfLinesInTheFile);
                totalNumberOfLinesInTheFile = 0; // �������� ������� � ��������� ��������
                System.out.println("����� �������� ������ � �����: " + shortestLineInTheFile);
                shortestLineInTheFile = 1025; // �������� ������� � ��������� ��������
                System.out.println("����� ������� ������ � �����: " + longestLineInTheFile);
                longestLineInTheFile = 0; // �������� ������� � ��������� ��������
            }
        }
    }
}
