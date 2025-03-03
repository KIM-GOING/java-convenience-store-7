package store.repository;

import store.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public ProductRepository() {
        loadProducts();
    }

    // 파일 읽어오기
    private void loadProducts() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("products.md");

        // 파일 존재 않을 시 예외처리
        if (inputStream == null) {
            throw new RuntimeException("[ERROR] 'products.md' file not found");
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                // 첫 줄 건너뛰기
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // 한 줄씩 파싱 후 products 리스트에 저장
                Product product = parseProduct(line);
                if (product != null) {
                    products.add(product);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("[ERROR] IO error reading products file");
        }

    }

    // 문자 분리하기
    private Product parseProduct(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            return null;
        }

        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        String promotion = parts[3].trim();

        return new Product(name, price, quantity, promotion);
    }
}
