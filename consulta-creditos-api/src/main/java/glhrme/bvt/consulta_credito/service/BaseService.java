package glhrme.bvt.consulta_credito.service;

import glhrme.bvt.consulta_credito.model.BaseModel;
import glhrme.bvt.consulta_credito.utils.validators.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributesException;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseService<T extends BaseModel, R extends JpaRepository<T, Long>> {

    protected R repo;

    private final List<Validator<T>> saveValidators;
    private final List<Validator<T>> deletionValidators;

    protected BaseService(R repo, List<Validator<T>> saveValidators, List<Validator<T>> deletionValidators) {
        this.repo = repo;
        this.saveValidators = saveValidators;
        this.deletionValidators = deletionValidators;
    }

    @Transactional(rollbackFor = Exception.class)
    public T save(T model) throws InvalidAttributesException {
        validateSaving(model);
        return repo.save(model);
    }

    private void validateSaving(T model) throws InvalidAttributesException {
        for(Validator<T> validator : saveValidators) {
            validator.validate(model);
        }
    }

    public void validateDeletion(T model) throws InvalidAttributesException {
        for(Validator<T> validator : deletionValidators) {
            validator.validate(model);
        }
    }

    public T findById(Long id, Class<T> modelClass) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("There is no record of " + modelClass.getSimpleName() +  " with the given id."));
    }

    public List<T> findAll() {
        return repo.findAll();
    }

    public void delete(T model) throws InvalidAttributesException {
        validateDeletion(model);
        repo.delete(model);
    }
}