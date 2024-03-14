package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.SpecsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This service class is the service that provides the main functionality for creating specs.
 * It is used to interact with two dataRepositories: (1) ComponentSpecsRepository and (2) SpecsRepository.
 * <p>
 * ComponentSpecs: The table in the database that contains the spec number and the value of the spec.
 * Specs: The table in the database that contains the name of the spec and the datatype.
 * <p>
 * Every componentSpecs entry is linked to a spec entry with an SpecID.
 */
@Service
public class CreateSpecsService {

    private static final Logger log = LoggerFactory.getLogger(CreateSpecsService.class);
    private final ComponentSpecsRepository componentSpecsRepository;
    private final SpecsRepository specsRepository;

    private final List<String> alreadyUsedSpecs = new ArrayList<>();

    @Autowired
    public CreateSpecsService(ComponentSpecsRepository componentSpecsRepository, SpecsRepository specsRepository) {
        this.componentSpecsRepository = componentSpecsRepository;
        this.specsRepository = specsRepository;
    }

    /**
     * Method that creates a new ComponentSpecs:
     *  1. The already used specs are retrieved from database
     *  2. The specs are iterated and checked if they already exist:
     *      2a. If the spec does not exist, it is created and saved to the database
     *      2b. If the spec already exists, it is not added again
     *  3. The given value is added to the new or existing spec
     *  4. new ComponentSpecs is saved to the database using the saveComponentSpecs method
     *
     * @param specs All the specs of the component
     * @param component The component to which the specs belong
     */
    public void createComponentSpecs(Map<String, Object> specs, Component component) {
        Iterable<Specs> specsIterable = this.specsRepository.findAll();

        // TODO: Maybe it's better to loop through all the specs and
        //  check directly if the spec exists in the specs HashMap,
        //  Then we have only one nest loop...?
        specsIterable.forEach(spec -> this.alreadyUsedSpecs.add(spec.getName()));

        for (String key : specs.keySet()) {
            ComponentSpecs componentSpecs = new ComponentSpecs();
            componentSpecs.setComponent(component);
            key = key.toLowerCase();
            Specs spec;
            if (this.alreadyUsedSpecs.contains(key)) {
                spec = this.specsRepository.findByName(key);
            } else {
                spec = new Specs();
                spec.setName(key);
                this.specsRepository.save(spec);
                log.info("A new Spec is created and saved to the database");
            }
            componentSpecs.setSpecs(spec);
            componentSpecs.setValue(specs.get(key).toString());
            this.saveComponentSpecs(componentSpecs);
            log.info("ComponentSpec is successfully saved to the database");
        }
    }

    /**
     * Method that saves the component specs to the database.
     * It calls the componentSpecsRepository to save the component specs.
     *
     * @param componentSpecs ComponentSpecs to be saved
     */
    protected void saveComponentSpecs(ComponentSpecs componentSpecs) {
        try {
            this.componentSpecsRepository.save(componentSpecs);
        } catch (Exception e) {
            log.error("Failed to save component specs: " + e.getMessage());
        }
    }

    /**
     * Method that deletes the component specs from the database.
     * All the possible component specs are retrieved from the database,
     * and then deleted using the componentSpecsRepository.
     *
     * @param component The component of which the specs need to be deleted
     */
    public void deleteComponentSpecs(Component component) {
        try {
            List<ComponentSpecs> componentSpecsList = this.componentSpecsRepository.findByComponent(component);
            this.componentSpecsRepository.deleteAll(componentSpecsList);
        } catch (Exception e) {
            log.error("Failed to delete component specs: " + e.getMessage());
        }
    }
}
