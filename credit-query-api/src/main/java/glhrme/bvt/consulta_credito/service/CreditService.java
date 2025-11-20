package glhrme.bvt.consulta_credito.service;

import glhrme.bvt.consulta_credito.model.Credit;
import glhrme.bvt.consulta_credito.repository.CreditRepository;
import glhrme.bvt.consulta_credito.utils.RecordNotFoundException;
import glhrme.bvt.consulta_credito.utils.validators.CreditBasicAttributesValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class CreditService extends BaseService<Credit, CreditRepository> {

    protected CreditService(CreditRepository repo) {
        super(repo, List.of(new CreditBasicAttributesValidator()), Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public List<Credit> findByInvoiceNumber(String invoiceNumber) {

        if(!StringUtils.hasText(invoiceNumber)) {
            throw new IllegalArgumentException("An invoice number is required to query credits.");
        }

        return repo.findByInvoiceNumber(invoiceNumber);
    }

    @Transactional(readOnly = true)
    public Credit findByCreditNumber(String creditNumber) {

        if(!StringUtils.hasText(creditNumber)) {
            throw new IllegalArgumentException("A credit number is required to query a credit.");
        }

        return repo.findByCreditNumber(creditNumber)
                            .orElseThrow(()-> new RecordNotFoundException("There is no credit with the given number."));
    }
}