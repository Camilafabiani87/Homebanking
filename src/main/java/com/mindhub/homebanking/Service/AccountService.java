package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import java.util.List;
public interface AccountService {
    public List<AccountDTO> getAccountSDTO();
    AccountDTO getAccountDTO(long id);
    public Account findByNumber(String number);

    public Account getAccountId(Long id);
    public void saveAccount(Account account);
}
