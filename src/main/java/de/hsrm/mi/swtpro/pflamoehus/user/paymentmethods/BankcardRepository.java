package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankcardRepository extends JpaRepository<Bankcard, Long> {
    Bankcard findByOwner (String ower);
}
