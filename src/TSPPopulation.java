import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TSPPopulation {

    private List<TSPChromosome> population;
    private final int initialSize;

    TSPPopulation(final TSP_Gene[] points, final int initialSize){
        this.population = init(points, initialSize);
        this.initialSize = initialSize;
    }

    TSPChromosome getAlpha(){
        return this.population.get(0); //returns best sorted value in the population
    }

    private List<TSPChromosome> init(final TSP_Gene[] points, final int initialSize){
        final List<TSPChromosome> eden = new ArrayList<>(); //initial population
        for(int i = 0; i < initialSize; i++){
            final TSPChromosome chromosome = TSPChromosome.create(points);
            eden.add(chromosome);
        }
        return eden;
    }

    void update(){
        doCrossOVer();
        doMutation();
        doSpawn();
        doSelection();
    }

    private void doSelection() {
        //sort population by fittest
        this.population.sort(Comparator.comparingDouble(TSPChromosome::calculateDistance));
        //keep size of population constant, but only keep fittest individuals
        this.population = this.population.stream().limit(this.initialSize).collect(Collectors.toList());
    }

    private void doSpawn() {
        //create 1k new individuals each generation for some fresh blood in each generation
        IntStream.range(0, 1000).forEach(e -> this.population.add(TSPChromosome.create(TSPUtils.CITIES)));
    }

    private void doMutation() {
        final List<TSPChromosome> newPopulation = new ArrayList<>();
        for(int i = 0; i < this.population.size()/10; i++){
            TSPChromosome mutation = this.population.get(TSPUtils.randomIndex(this.population.size())).mutate();
            newPopulation.add(mutation);
        }
        this.population.addAll(newPopulation);
    }

    private void doCrossOVer(){
        final List<TSPChromosome> newPopulation = new ArrayList<>();
        for(final TSPChromosome chromosome : this.population){
            final TSPChromosome partner = getCrossOverPartner(chromosome);
            newPopulation.addAll(Arrays.asList(chromosome.crossOver(partner))); //add to the new population the result of doing the crossover
        }
        this.population.addAll(newPopulation);//so we don't lose the existing members
    }

    private TSPChromosome getCrossOverPartner(TSPChromosome chromosome) {
        TSPChromosome partner = this.population.get(TSPUtils.randomIndex(this.population.size()));
        while (chromosome == partner){
            //don't want to crossover with ourselves
            partner = this.population.get(TSPUtils.randomIndex(this.population.size()));
        }
        return partner;
    }
}
