import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static org.apache.poi.hssf.model.InternalSheet.createSheet;



public class Storage {

    private static ArrayList<String> goods;
    Scanner scan = new Scanner(System.in);


    public Storage() throws IOException {
        workStorage();
    }


    private void workStorage() throws IOException {


        goods = new ArrayList<>();
        goods.add("Чебурашка");
        goods.add("Матрешка");
        goods.add("Балалайка");
        goods.add("Водка");
        goods.add("Самурай");
        goods.add("Сакура");
        goods.add("Удон");


        System.out.println("Привет! Ты находишься на складе" + "\n");
        System.out.println("Команды для работы со складом выглядят так:" + "\n"
                + "Если ты хочешь увидеть наименования товаров, которые лежат на складе, введи команду view" + "\n"
                + "Если ты хочешь добавить товар на склад, введи команду add" + "\n"
                + "Если ты хочешь удалить товар со склада, введи команду remove" + "\n"
                + "Если ты хочешь проверить наличие элемента на складе, введи команду contains" + "\n"
                + "Если ты хочешь узнать сколько всего единиц товара лежит на складе, введи команду size" + "\n"
                + "Если ты хочешь узнать количество товара на складе в виде 'товар-количество', введи команду count" + "\n"
                + "Если ты хочешь выгрузить перечень товара со склада, введи команду export" + "\n"
                + "Если ты хочешь закрыть программу, введи команду exit");


        while (true) {
            String method = scan.nextLine();

            if (method.equals("exit")) {
                System.out.println("Выход из программы");
                break;
            }

            switch (method) {
                case "view" -> System.out.println("Cейчас на складе лежат эти товары " + goods);
                case "add" -> {
                    System.out.println("Введи наименование товара, который ты хочешь добавить: ");
                    goods.add(scan.nextLine());
                    System.out.println("Cейчас на складе лежат эти товары " + goods);
                }
                case "remove" -> {
                    System.out.println("Cейчас на складе лежат эти товары " + goods);
                    System.out.println("Введи наименование товара, который ты хочешь удалить:");
                    goods.remove(scan.nextLine());
                    System.out.println("А вот сейчас на складе лежат эти товары " + goods);
                }
                case "contains" -> {
                    System.out.println("Введи наименование товара, наличие которого ты хочешь проверить:");
                    if (goods.contains(scan.nextLine()) == goods.equals(goods)) {
                        System.out.println("На складе это есть");
                    } else {
                        System.out.println("На складе этого нет");
                    }
                }
                case "size" -> System.out.println("На складе всего " + goods.size() + " единиц товара");

                case "count" -> {
                    Map<Object, Long> counterMap = goods.stream().collect(Collectors.groupingBy(String::toString, Collectors.counting()));
                    System.out.println(counterMap);
                }

                case "export" -> createExel();

                default -> System.out.println("Неверная команда, попробуй еще");

            }

            System.out.println("Еще что-нибудь?");





        }
    }




    public static void createExel() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Storage");
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Товары на складе:");


        for (int i = 1; i < goods.size(); i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(goods.get(i));
        }


            sheet.autoSizeColumn(goods.size());


            File file = new File("storage.xlsx");
            FileOutputStream outFile = new FileOutputStream(file);
            wb.write(outFile);
            outFile.close();

            System.out.println("Выгрузка файла");

        }

    }





