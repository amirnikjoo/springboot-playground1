package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonalInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonalInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long>, JpaSpecificationExecutor<PersonalInfo> {}
