package cs601.project1.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a data structure which maps terms to the documents where those terms appear.
 *
 * @author Palak Jain
 */
public class InvertedIndex {
    private HashMap<String, List<Document>> wordMap;

    public InvertedIndex() {
        wordMap = new HashMap<>();
    }

    /**
     * Creates new entry by keeping term as key and value as its document reference or update existing one by adding new document where the term exist.
     * @param key one word term
     * @param document Holding an index of the actual document where the term exists.
     */
    public void upsert(String key, Document document) {
        List<Document> documentRefs = wordMap.getOrDefault(key, new ArrayList<>());
        documentRefs.add(document);

        wordMap.put(key, documentRefs);
    }

    /**
     * Gets all the documents where the given term exists.
     * @param term One word term
     * @param isPartial
     * @return
     */
    public List<Document> get(String term, boolean isPartial) {
        List<Document> documents = new ArrayList<>();

        if(!isPartial) {
            documents = wordMap.getOrDefault(term, null);
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
}
