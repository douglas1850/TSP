//helper methods here

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TSPUtils {
    private final static Random R = new Random(10000);

    static final TSP_Gene[] CITIES = generateData(100); //100 cities to generate

    private TSPUtils(){
        throw new RuntimeException("whoops2");
    }

    private static TSP_Gene[] generateData(final int dataPoints){
        final TSP_Gene[] data = new TSP_Gene[dataPoints];
        //creating data points on random coordinate
        for(int i = 0; i < dataPoints; i++){
            data[i] = new TSP_Gene(TSPUtils.randomIndex(World.WIDTH),
                    TSPUtils.randomIndex(World.HEIGHT));
        }

        return data;
    }

    static int randomIndex(final int limit){
        return R.nextInt(limit); //get random # between 0 and limit-1
    }

    static<T>List<T>[] split(final List<T> list){
        final List<T> first = new ArrayList<>();
        final List<T> second = new ArrayList<>();
        final int size = list.size();
        IntStream.range(0, size).forEach(i ->{
            if(i < (size+1)/2){
                first.add(list.get(i));
            } else{
                second.add((list.get(i)));
            }
        });
        return (List<T>[]) new List[] {first, second};
    }
}
