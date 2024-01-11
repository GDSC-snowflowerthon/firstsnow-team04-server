package com.dev.firstsnow.Repository;

import com.dev.firstsnow.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Letter, Long> {
}
