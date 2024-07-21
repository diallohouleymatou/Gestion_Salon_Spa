package Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UtilsFonction {

    private static final int PHONE_NUMBER_LENGTH = 9;
    private static final List<String> VALID_PREFIXES = Arrays.asList("77", "78", "76", "75", "72", "70");


    public static <T> void displayDataInTable(List<T> dataList, JTable jTable) {
        displayDataInTable(dataList, jTable, new ArrayList<>());
    }

    public static <T> void displayDataInTable(List<T> dataList, JTable jTable, List<String> columnsToOmit) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        Class<?> itemClass = dataList.get(0).getClass();
        Field[] allFields = itemClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : allFields) {
            if (!Modifier.isStatic(field.getModifiers()) && !columnsToOmit.contains(field.getName())) {
                fields.add(field);
            }
        }

        String[] columnNames = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i] = camelCaseToText(fields.get(i).getName());
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (T item : dataList) {
            Object[] rowData = new Object[fields.size()];
            for (int i = 0; i < fields.size(); i++) {
                try {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    Object fieldValue = field.get(item);

                    if (fieldValue instanceof Date) {
                        rowData[i] = dateFormat.format((Date) fieldValue);
                    } else {
                        rowData[i] = fieldValue;
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }
        jTable.setModel(model);
    }

    public static String camelCaseToText(String camelCaseString) {
        if (isEmpty(camelCaseString)) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        convertFirstCharacter(camelCaseString, result);
        convertRestCharacters(camelCaseString, result);

        return result.toString();
    }

    private static boolean isEmpty(String camelCaseString) {
        return camelCaseString.trim() == null || camelCaseString.trim().isEmpty();
    }

    private static void convertFirstCharacter(String camelCaseString, StringBuilder result) {
        char firstCharacter = camelCaseString.charAt(0);
        result.append(Character.toUpperCase(firstCharacter));
    }

    private static void convertRestCharacters(String camelCaseString, StringBuilder result) {
        for (int i = 1; i < camelCaseString.length(); i++) {
            char currentChar = camelCaseString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append(' ');
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }
    }

    private static String removeAllNonDigitCharacters(String text) {
        return text.replaceAll("[^\\d]", "");
    }

    public static String formatNumber(String text) {
        String cleanText = removeAllNonDigitCharacters(text);

        while (cleanText.length() < PHONE_NUMBER_LENGTH) {
            cleanText = "0" + cleanText;
        }

        String formattedText = cleanText.substring(0, 2) + " "
                + cleanText.substring(2, 5) + " "
                + cleanText.substring(5, 7) + " "
                + cleanText.substring(7, 9);

        return formattedText;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String cleanPhoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        if (cleanPhoneNumber.length() != PHONE_NUMBER_LENGTH) {
            return false;
        }

        String prefix = cleanPhoneNumber.substring(0, 2);
        return VALID_PREFIXES.contains(prefix);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

}


