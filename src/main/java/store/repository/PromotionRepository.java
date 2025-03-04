package store.repository;

import store.model.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PromotionRepository {
    private List<Promotion> promotions =  new ArrayList<>();

    public PromotionRepository() {
        loadPromotions();
    }

    // 파일 읽어오기
    private void loadPromotions() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("promotions.md");

        // 파일 존재 않을 시 예외처리
        if (inputStream == null) {
            throw new RuntimeException("[ERROR] 'promotions.md' file not found");
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

                // 한 줄씩 파싱 후 promotions 리스트에 저장
                Promotion promotion = parsePromotion(line);
                if (promotion != null) {
                    promotions.add(promotion);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("[ERROR] IO error reading promotions file");
        }

    }

    // 문자 분리하기
    private Promotion parsePromotion(String line) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String[] parts = line.split(",");
        if (parts.length != 5) {
            return null;
        }

        try {
            String name = parts[0].trim();
            int buy = Integer.parseInt(parts[1].trim());
            int get = Integer.parseInt(parts[2].trim());
            Date startDate = df.parse(parts[3].trim());
            Date endDate = df.parse(parts[4].trim());

            return new Promotion(name, buy, get, startDate, endDate);
        }
        catch (Exception e) {
            throw new RuntimeException("[ERROR] Text file format error");
        }
    }
}
