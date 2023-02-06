package com.tfa.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.tfa.dto.EvenementCommande;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandeProducer {

	private final KafkaTemplate<String, EvenementCommande> template;
	private final NewTopic topic;
	
	@Autowired
	public CommandeProducer(KafkaTemplate<String, EvenementCommande> template, NewTopic topic) {
		super();
		this.template = template;
		this.topic = topic;
	}
	
	public void envoieCommande(EvenementCommande commande) {
		log.info("Envoie de commande {}",commande);
		Message<EvenementCommande> message = MessageBuilder.withPayload(commande)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		template.send(message);
	}
	
}
