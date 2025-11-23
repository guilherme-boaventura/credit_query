package glhrme.bvt.creditquery.utils.kafka;

public record KafkaMessage(String id, String type, String timestamp, Object payload) {
}
