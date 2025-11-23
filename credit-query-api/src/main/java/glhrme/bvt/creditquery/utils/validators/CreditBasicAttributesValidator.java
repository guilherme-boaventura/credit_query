package glhrme.bvt.creditquery.utils.validators;

import glhrme.bvt.creditquery.model.Credit;
import org.springframework.util.StringUtils;

import javax.naming.directory.InvalidAttributesException;
import java.util.Objects;

//example validator that would be used to validate the basic attributes of a credit in case it needed to be created.
public class CreditBasicAttributesValidator implements Validator<Credit> {

    @Override
    public void validate(Credit credit) throws InvalidAttributesException {
        if(!StringUtils.hasText(credit.getInvoiceNumber())) {
            throw new InvalidAttributesException("The credit invoice number must be provided.");
        }
        if(!StringUtils.hasText(credit.getCreditNumber())) {
            throw new InvalidAttributesException("The credit number must be provided.");
        }
        if(Objects.isNull(credit.getEstablishmentDate())) {
            throw new InvalidAttributesException("The credit number must be provided.");
        }
        if(Objects.isNull(credit.getIssqnValue())) {
            throw new InvalidAttributesException("The ISSQN value must be provided.");
        }
        if (!StringUtils.hasText(credit.getCreditType())) {
            throw new InvalidAttributesException("The credit type must be provided.");
        }
        if (Objects.isNull(credit.getTaxRate())) {
            throw new InvalidAttributesException("The tax rate must be provided.");
        }
        if (Objects.isNull(credit.getBilledAmount())) {
            throw new InvalidAttributesException("The billed amount must be provided.");
        }
        if (Objects.isNull(credit.getDeductionAmount())) {
            throw new InvalidAttributesException("The deduction amount must be provided.");
        }
        if (Objects.isNull(credit.getCalculationBase())) {
            throw new InvalidAttributesException("The calculation base must be provided.");
        }
    }
}
