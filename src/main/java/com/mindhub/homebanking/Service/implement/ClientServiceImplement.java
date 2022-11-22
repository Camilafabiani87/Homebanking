package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ColorType;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Override
    public List<ClientDTO> getClientsDTO() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @Override
    public ClientDTO getClientDTO(long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }
    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail((email));
    }
    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }



}
