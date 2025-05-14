package in.chatMe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.chatMe.entity.Account;

@Repository
public interface UserRepo extends JpaRepository<Account, Long>
{
	Account findByEmail(String email);
    List<Account> findByEmailNot(String email);
}
