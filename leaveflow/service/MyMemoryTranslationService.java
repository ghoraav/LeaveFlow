package com.leaveflow.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MyMemoryTranslationService {

    // The API endpoint. %s will be replaced by our text and language pair.
    private static final String API_URL = "https://api.mymemory.translated.net/get?q={q}&langpair={langpair}";

    @Autowired
    private RestTemplate restTemplate;

    public String translateToEnglish(String text, String language) {
        // If it's already English, no need to translate
        if ("en".equalsIgnoreCase(language) || text == null || text.isEmpty()) {
            return text;
        }

        try {
            // 1. Create the language pair (e.g., "hi|en")
            String langPair = language.toLowerCase() + "|en";

            // 2. Call the API
            // We pass the raw 'text' and 'langPair' as variables.
            // RestTemplate will now handle the URL encoding for us.
            MyMemoryResponse response = restTemplate.getForObject(API_URL, MyMemoryResponse.class, text, langPair);

            // 3. Extract and return the translated text
            if (response != null && response.getResponseData() != null) {
                return response.getResponseData().getTranslatedText();
            }

        } catch (Exception e) {
            // If the API fails for any reason (e.g., no internet),
            // log the error and return the original text with a note.
            System.err.println("Translation failed: " + e.getMessage());
            return text + " [Translation Failed]";
        }
        
        // Fallback
        return text + " [Translation Failed]";
    }

    // ---
    // Helper classes to match the JSON response from the API
    // Jackson will use these to automatically parse the JSON
    // ---

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MyMemoryResponse {
        @JsonProperty("responseData")
        private ResponseData responseData;

        public ResponseData getResponseData() {
            return responseData;
        }
        public void setResponseData(ResponseData responseData) {
            this.responseData = responseData;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData {
        @JsonProperty("translatedText")
        private String translatedText;

        public String getTranslatedText() {
            return translatedText;
        }
        public void setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
        }
    }
}