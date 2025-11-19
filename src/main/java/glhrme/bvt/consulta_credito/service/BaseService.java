package glhrme.bvt.consulta_credito.service;

import glhrme.bvt.consulta_credito.model.BaseModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributesException;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseService<T extends BaseModel, R extends JpaRepository<T, Long>> {

    protected R repo;

    protected BaseService(R repo) {
        this.repo = repo;
    }

    @Transactional(rollbackFor = Exception.class)
    public T save(T model) throws InvalidAttributesException {
        validate(model);
        validateUniqueness(model);
        return repo.save(model);
    }

    public void validate(T model) throws InvalidAttributesException {

    }

    public void validateUniqueness(T model) throws DataIntegrityViolationException {

    }

    public void validateDeletion(T model) throws DataIntegrityViolationException {

    }

    public T findById(Long id, Class<T> modelClass) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("There is no record of " + modelClass.getSimpleName() +  " with the given id."));
    }

    public List<T> findAll() {
        return repo.findAll();
    }

    public void delete(T model) {
        validateDeletion(model);
        repo.delete(model);
    }
}