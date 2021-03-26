package code;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AscllSort3 {
    //�ڲ���ÿ��List����һ��������ʡ����Ϣ
    //��һ��λ�þ��Ǵ洢��ʡ������
    private static final List<List<String>> province = new ArrayList<>();

    private static final List<List<String>> sortProvince = new ArrayList<>();

    private static String result;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] split = line.split(" ");
        readFileByInFileName(split[0]);
        if (split.length == 3) {
            //ָ����Ҫ��ʡ����
            String[] strings = result.split("\n");
            StringBuilder builder = new StringBuilder();
            boolean flag = false;
            for (String string : strings) {
                int indexOf = string.indexOf("\t");
                String substring = indexOf > 0 ? string.substring(0, indexOf) : string;
                substring = substring.replace("\"", "").trim();
                if (substring.equals(split[2])) {
                    builder.append(string).append("\n");
                    flag = true;
                } else if (flag) {
                    break;
                }
            }
            writeFileByFileName(split[1], builder.toString());
        } else {
            writeFileByFileName(split[1]);
        }

    }

    private static void readFileByInFileName(String fileName) {
        //��ȡ�ļ�
        try (BufferedReader reader = new BufferedReader(
                new FileReader( fileName))) {
            //д���ļ�
            String line;
            //����¸�λ���ǲ���ʡ��
            //�¸���ʡ��Ļ��������´���һ���µļ���
            boolean flag = true;
            List<String> element = new ArrayList<>();
            //���ѭ�����ǻ�ȡ������Ϣ
            while ((line = reader.readLine()) != null) {
                if (flag) {
                    flag = false;
                }
                //��������ж��Ƿ���ʡ����߼�
                if ("".equals(line.trim())) {
                    province.add(element);
                    element = new ArrayList<>();
                    flag = true;
                    continue;
                }
                element.add(line);
                element.add("\n");
            }
        } catch (IOException ignored) {
        }
        //������ʡ�Ὺͷ���ʡ��
        province.forEach(AscllSort3::appendChar);
        province.forEach(list -> list.add(list.size() / 2 + ""));
        sort();
        System.out.println(sortProvince);
        result = province.toString().replace("]", "").replace("[", "").replace(",", "");
    }

    private static void sort() {
        for (int i = 0; i < province.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < province.size(); j++) {
                int i1 = Integer.parseInt(province.get(minIndex).get(province.get(minIndex).size() - 1).trim());
                int j1 = Integer.parseInt(province.get(j).get(province.get(j).size() - 1).trim());
                if (j1 < i1) {
                    minIndex = j;
                }
            }
            sortProvince.add(province.get(minIndex));
        }
    }

    private static void writeFileByFileName(String outFileName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter( outFileName))) {
            //д���ļ�
            writer.write(result);
            //���ڴ��е�����ˢ�µ�����
            writer.flush();
        } catch (IOException ignored) {
        }
    }

    private static void writeFileByFileName(String outFileName, String content) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter( outFileName))) {
            //д���ļ�
            writer.write(content);
            //���ڴ��е�����ˢ�µ�����
            writer.flush();
        } catch (IOException ignored) {
        }
    }

    private static void appendChar(List<String> list) {
        //ʡ����
        String head;
        //�Ӻδ���ʼ�ı���
        int i;
        if (list.get(0).trim().equals("")) {
            head = list.get(1);
            i = 2;
        } else {
            i = 1;
            head = list.get(0);
        }

        //�������
        for (; i < list.size(); i++) {
            String temp;
            if (!(temp = list.get(i)).trim().equals("")) {
                list.remove(i);
                list.add(i, head + "\t" + temp);
            }
        }
        //�Ƴ��ʼ��ʡ��
        list.remove(0);
    }

}