package glhrme.bvt.consulta_credito.utils.kafka;

public record KafkaMessage(String id, String type, String timestamp, Object payload) {
}
