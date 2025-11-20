package glhrme.bvt.consulta_credito.utils.validators;

import glhrme.bvt.consulta_credito.model.BaseModel;
import org.springframework.dao.DataIntegrityViolationException;

import javax.naming.directory.InvalidAttributesException;

public interface Validator<T extends BaseModel> {

    public void validate(T model) throws InvalidAttributesException;

}