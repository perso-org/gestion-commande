package com.tfa.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfa.dto.Commande;
import com.tfa.dto.EvenementCommande;
import com.tfa.producer.CommandeProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/stk")
@RequiredArgsConstructor
public class CommandeController {

	private final CommandeProducer producer;
	
	@PostMapping("/commandes")
	public ResponseEntity<String> envoinouvelleCommande(@RequestBody Commande commande) {
		commande.setIdCmd(UUID.randomUUID().toString().toUpperCase());
		EvenementCommande evenementCommande = EvenementCommande.builder()
				.status("En attente")
				.message("Commande en attente de prise en charge")
				.commande(commande)
				.build();
		producer.envoieCommande(evenementCommande);
		return ResponseEntity.ok(String.format("Commande %s en attente de prise en charge", commande.getIdCmd()));
	}
}
