package glhrme.bvt.consulta_credito.utils.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import glhrme.bvt.consulta_credito.model.Credito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class KafkaProducerService {

    private final String TOPICO_CONSULTA_CREDITO = "consulta_credito";

    @Autowired
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageConsultaNfse(String numeroNfse, List<Credito> creditos) {
        if(!StringUtils.hasText(numeroNfse) && Objects.nonNull(creditos)) {
            throw new IllegalArgumentException("O número da nota fiscal eletrônica consultada e a lista de créditos retornados devem ser informados para o envio da mensagem.");
        }

        Map<String, Object> payload = Map.of("numeroConsultado", numeroNfse, "retornoConsulta", creditos);

        kafkaTemplate.send(TOPICO_CONSULTA_CREDITO, new KafkaMessage(UUID.randomUUID().toString(), "obterPorNumeroNfse", ZonedDateTime.now().toString(), payload));
    }

    public void sendMessageConsultaNuCredito(String numeroCredito, Credito credito) {
        if(!StringUtils.hasText(numeroCredito)) {
            throw new IllegalArgumentException("O número do crédito consultado deve ser informado para o envio da mensagem.");
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("numeroConsultado", numeroCredito);
        payload.put("retornoConsulta", credito);

        kafkaTemplate.send(TOPICO_CONSULTA_CREDITO, new KafkaMessage(UUID.randomUUID().toString(), "obterPorNumeroCredito", ZonedDateTime.now().toString(), payload));
    }

}
