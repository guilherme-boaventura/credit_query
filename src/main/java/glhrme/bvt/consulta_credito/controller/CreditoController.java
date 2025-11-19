package glhrme.bvt.consulta_credito.controller;

import glhrme.bvt.consulta_credito.model.Credito;
import glhrme.bvt.consulta_credito.service.CreditoService;
import glhrme.bvt.consulta_credito.utils.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private KafkaProducerService kafkaProducer;

    @Autowired
    private CreditoService creditoService;

    @GetMapping("/{numeroNfse}")
    public List<Credito> obterPorNumeroNfse(@PathVariable("numeroNfse") String numeroNfse) {
        List<Credito> creditos = creditoService.findByNumeroNfse(numeroNfse);

        kafkaProducer.sendMessageConsultaNfse(numeroNfse, creditos);
        return creditos;
    }

    @GetMapping("/credito/{numeroCredito}")
    public Credito obterPorNumeroCredito(@PathVariable("numeroCredito") String numeroCredito) {
        Credito credito = null;

        try {
            credito = creditoService.findByNumeroCredito(numeroCredito);
        } finally {
            kafkaProducer.sendMessageConsultaNuCredito(numeroCredito, credito);
        }

        return credito;
    }
}