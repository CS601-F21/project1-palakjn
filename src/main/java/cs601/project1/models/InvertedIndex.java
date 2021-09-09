package cs601.project1.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    private HashMap<String, List<Document>> wordMap;

    public InvertedIndex() {
        wordMap = new HashMap<>();
    }

    public void upsert(String key, Document document) {
        List<Document> documentRefs = wordMap.getOrDefault(key, new ArrayList<>());
        documentRefs.add(document);

        wordMap.put(key, documentRefs);
    }

    public List<Document> get(String term, boolean isPartial) {
        List<Document> documents = new ArrayList<>();

        if(!isPartial) {
            documents = wordMap.get(term);
        }
        else {
            for (Map.Entry<String, List<Document>> entry : wordMap.entrySet()) {
                if(entry.getKey().contains(term)) {
                    documents.addAll(entry.getValue());
                }
            }
        }

        return documents;
    }

    public int getCount() {
        return wordMap.size();
    }
}
