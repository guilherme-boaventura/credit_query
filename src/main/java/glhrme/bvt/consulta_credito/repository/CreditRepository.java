package glhrme.bvt.consulta_credito.repository;

import glhrme.bvt.consulta_credito.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByInvoiceNumber(String invoiceNumber);

    Optional<Credit> findByCreditNumber(String creditNumber);
}