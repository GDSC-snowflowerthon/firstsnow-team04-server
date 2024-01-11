package com.dev.firstsnow.Repository;

import com.dev.firstsnow.domain.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
}
