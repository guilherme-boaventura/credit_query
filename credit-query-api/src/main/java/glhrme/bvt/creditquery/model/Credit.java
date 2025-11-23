package glhrme.bvt.creditquery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "credito")
public class Credit extends BaseModel {

    @Column(name="numero_credito")
    private String creditNumber;

    @Column(name="numero_nfse")
    private String invoiceNumber;

    @Column(name="data_constituicao")
    private LocalDate establishmentDate;

    @Column(name="valor_issqn")
    private BigDecimal issqnValue;

    @Column(name="tipo_credito")
    private String creditType;

    @Column(name="simples_nacional")
    private boolean simpleNational;

    @Column(name="aliquota")
    private BigDecimal taxRate;

    @Column(name="valor_faturado")
    private BigDecimal billedAmount;

    @Column(name="valor_deducao")
    private BigDecimal deductionAmount;

    @Column(name="base_calculo")
    private BigDecimal calculationBase;

}