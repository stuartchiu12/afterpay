package afterpay;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Runner {

    private Map<String, List<TimeStampSpend>> memory = new HashMap();

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    /**
     * Returns true if sum of list within a 24hours sliding window is equal or under threshold
     * @param threshold
     * @param list
     * @return
     */
    public boolean isValid(double threshold,List<TimeStampSpend> list){

        for(int i=0;i<list.size();i++){
           double prices = 0;
           var pointer = list.get(i);
           prices+=pointer.getDollarAndCents();
           for(int j=i+1;j<list.size();j++){
               var current = list.get(j);
               var hours = ChronoUnit.HOURS.between(pointer.getDateTime(), current.getDateTime());
               logger.debug("calc hours {} with time start from {} to {}", hours, pointer.getDateTime(), current.getDateTime());
               if(hours>=24){
                   break;
               }
               prices+=current.getDollarAndCents();
           }
           logger.debug("comparing prices {} with threshold {}", prices, threshold);
           if(prices>threshold) return false;
        }
        return true;
    }

    /**
     * return a list of keys in the CSV that is "valid", check isValid for that considers as valid
     * @param reader
     * @param threshold
     * @return
     */
    public List<String> run(Reader reader, double threshold) {
        CSVReader csvReader = new CSVReader(reader);
        var iterator = csvReader.iterator();
        while(iterator.hasNext()){
            var lines = iterator.next();
            var key = lines[0].trim();
            var timestamp = new TimeStampSpend(lines[1].trim(),lines[2].trim());
            if(memory.containsKey(key)){
                memory.get(key).add(timestamp);
            }else{
                memory.put(key, new ArrayList<>(List.of(timestamp)));
            }
        }
        return memory.keySet().stream()
                .filter(key->!isValid(threshold, memory.get(key)))
                .collect(Collectors.toUnmodifiableList());

    }


    public static void main(String[] args) throws IOException {
        double threshold = Double.parseDouble(args[0]);
        File file = new File(args[1]);
        var runner = new Runner();
        List<String> invalidKeys =  runner.run(new FileReader(file), threshold);
        for(String k:invalidKeys){
            System.out.println("invalid key "+k);
        }
    }
}
