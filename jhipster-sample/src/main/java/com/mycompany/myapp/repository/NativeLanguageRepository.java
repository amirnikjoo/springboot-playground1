package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NativeLanguage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NativeLanguage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NativeLanguageRepository extends JpaRepository<NativeLanguage, Long>, JpaSpecificationExecutor<NativeLanguage> {}
