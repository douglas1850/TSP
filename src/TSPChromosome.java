import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSPChromosome {

    private final List<TSP_Gene> chromosome;

    TSPChromosome(final List<TSP_Gene> chromosome){
        this.chromosome = Collections.unmodifiableList(chromosome);
    }

    //array of data points to give problem
    static TSPChromosome create(final TSP_Gene[] points){//create random solutions for initial population
        //copy original array into a list so as to not modify the original
        final List<TSP_Gene> genes = Arrays.asList(Arrays.copyOf(points, points.length));
        Collections.shuffle(genes); //shuffle to get a random solution
        return new TSPChromosome(genes);
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(final TSP_Gene gene : this.chromosome){
            builder.append(gene.toString()).append((" : "));
        }
        return builder.toString(); //print out each one of the genes. Will be ordered because it's in a list
    }

    List<TSP_Gene> getChromosome(){
        return this.chromosome;
    }

    //fitness function. Program spends most of its time in this function
    double calculateDistance(){
        double total = 0.0f;
        for (int i = 0; i <this.chromosome.size() -1; i++){
            total += this.chromosome.get(i).distance(this.chromosome.get(i+1));
        }
        return total; //return distance traversed between all genes in the chromosome
    }

    //return TSPChromosome array
    TSPChromosome[] crossOver(final TSPChromosome other){
        //split list of chromosomes in two halfs
        //take 'other' chromosone and do the same thing
        //then use algorithm to mix them together
        final List<TSP_Gene>[] myDNA = TSPUtils.split(this.chromosome); //array with 2 elements, each being a list type
        final List<TSP_Gene>[] otherDNA = TSPUtils.split(other.getChromosome());

        //should end up with half of first and second DNA sequences being paired
        final List<TSP_Gene> firstCrossOver = new ArrayList<>(myDNA[0]);
        for(TSP_Gene gene : otherDNA[0]){
            if(!firstCrossOver.contains(gene)){ //if 1st crossover doesn't contain gene, add it
                firstCrossOver.add(gene);
            }
        }
        for(TSP_Gene gene : otherDNA[1]){
            if(!firstCrossOver.contains(gene)){
                firstCrossOver.add(gene);
            }
        }

        final List<TSP_Gene> secondCrossOver = new ArrayList<>(otherDNA[1]);

        for(TSP_Gene gene : myDNA[0]){
            if(!secondCrossOver.contains(gene)){
                secondCrossOver.add(gene);
            }
        }
        for(TSP_Gene gene : myDNA[1]){
            if(!secondCrossOver.contains(gene)){
                secondCrossOver.add(gene);
            }
        }

        if(firstCrossOver.size() != TSPUtils.CITIES.length ||
                secondCrossOver.size() != TSPUtils.CITIES.length){
            throw new RuntimeException("whoops");
        }
        //doesn't modify existing sequence, creates two new ones
        return new TSPChromosome[]{
                new TSPChromosome(firstCrossOver),
                new TSPChromosome(secondCrossOver)
        };
    }

    //mutate chromosome
    TSPChromosome mutate() {

        final List<TSP_Gene> copy = new ArrayList<>(this.chromosome);
        int indexA = TSPUtils.randomIndex(copy.size());
        int indexB = TSPUtils.randomIndex(copy.size());
        while (indexA == indexB){
            indexA = TSPUtils.randomIndex(copy.size());
            indexB = TSPUtils.randomIndex(copy.size());
        }
        Collections.swap(copy, indexA, indexB); //swap two cities (indexes) in the traversal
        return new TSPChromosome(copy);
    }
}
