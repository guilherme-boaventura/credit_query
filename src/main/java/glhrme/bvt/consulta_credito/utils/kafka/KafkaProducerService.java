package glhrme.bvt.consulta_credito.utils.kafka;

import glhrme.bvt.consulta_credito.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class KafkaProducerService {

    private final String CREDIT_QUERY_TOPIC = "credit_query";

    @Autowired
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageInvoiceNumberQuery(String invoiceNumber, List<Credit> credits) {
        if(!StringUtils.hasText(invoiceNumber) && Objects.nonNull(credits)) {
            throw new IllegalArgumentException("The queried invoice number and the query result must be provided to send the message.");
        }

        Map<String, Object> payload = Map.of("queriedNumber", invoiceNumber, "queryResult", credits);

        kafkaTemplate.send(CREDIT_QUERY_TOPIC, new KafkaMessage(UUID.randomUUID().toString(), "findByInvoiceNumber", ZonedDateTime.now().toString(), payload));
    }

    public void sendMessageCreditNumberQuery(String creditNumber, Credit credit) {
        if(!StringUtils.hasText(creditNumber)) {
            throw new IllegalArgumentException("The queried credit number must be provided to send the message.");
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("queriedNumber", creditNumber);
        payload.put("queryResult", credit);

        kafkaTemplate.send(CREDIT_QUERY_TOPIC, new KafkaMessage(UUID.randomUUID().toString(), "findByCreditNumber", ZonedDateTime.now().toString(), payload));
    }

}
