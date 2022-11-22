package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ColorType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
public interface ClientService {
    public List<ClientDTO> getClientsDTO();
    public ClientDTO getClientDTO(long id);
    public Client findByEmail(String Email);
    public void saveClient(Client client);

}
