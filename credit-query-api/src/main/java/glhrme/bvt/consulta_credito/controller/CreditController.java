package glhrme.bvt.consulta_credito.controller;

import glhrme.bvt.consulta_credito.model.Credit;
import glhrme.bvt.consulta_credito.service.CreditService;
import glhrme.bvt.consulta_credito.utils.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    private KafkaProducerService kafkaProducer;

    @Autowired
    private CreditService creditService;

    @GetMapping("/invoice/{invoiceNumber}")
    public List<Credit> findByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber) {
        List<Credit> credits = creditService.findByInvoiceNumber(invoiceNumber);

        kafkaProducer.sendMessageInvoiceNumberQuery(invoiceNumber, credits);
        return credits;
    }

    @GetMapping("/{creditNumber}")
    public Credit findByCreditNumber(@PathVariable("creditNumber") String creditNumber) {
        Credit credit = null;

        try {
            credit = creditService.findByCreditNumber(creditNumber);
        } finally {
            kafkaProducer.sendMessageCreditNumberQuery(creditNumber, credit);
        }

        return credit;
    }
}