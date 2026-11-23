package utils;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataProviders {

    // =========================================================
    // JSON Data Provider
    // =========================================================

    @DataProvider
    public Object[][] jsonDataProvider() throws IOException {

        // Path to your JSON file
        String filePath = "src/test/resources/product.json";

        // Read JSON file and map it to List of Maps
        ObjectMapper objectMapper = new ObjectMapper();

        List<java.util.Map<String, String>> dataList =
                objectMapper.readValue(
                        new File(filePath),
                        new TypeReference<List<java.util.Map<String, String>>>() {
                        }
                );

        // Convert List<Map<String,String>> to Object[][]
        Object[][] dataArray = new Object[dataList.size()][];

        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i] = new Object[] {
                    dataList.get(i)
            };
        }

        return dataArray;
    }


    // =========================================================
    // CSV Data Provider
    // =========================================================

    @DataProvider
    public Object[][] csvDataProvider() throws IOException {

        // Path to your CSV file
        String filePath = ".\\testdata\\product.csv";

        // Read CSV file and store data in a List
        List<String[]> dataList = new ArrayList<>();

        try (java.io.BufferedReader br =
                     new java.io.BufferedReader(new FileReader(filePath))) {

            // Skip the first line (header)
            br.readLine();

            String line;

            while ((line = br.readLine()) != null) {

                // Split by comma
                String[] data = line.split(",");

                dataList.add(data);
            }
        }

        // Convert List<String[]> to Object[][]
        Object[][] dataArray = new Object[dataList.size()][];

        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i] = dataList.get(i);
        }

        return dataArray;
    }
}
